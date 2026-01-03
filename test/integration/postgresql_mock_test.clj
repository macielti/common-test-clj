(ns postgresql-mock-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.component.postgresql-mock :as component.postgresql-mock]
            [integrant.core :as ig]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [pg.core :as pg]
            [schema.test :as s])
  (:import (org.pg Pool)))

(def config {::component.postgresql-mock/postgresql-mock {:components {:config {:postgresql-migrations {:migrations-table :migrations
                                                                                                        :migrations-path  "migrations"}}}}})

(s/deftest postgresql-mock-component-test
  (let [system (ig/init config)
        pool (:common-test-clj.component.postgresql-mock/postgresql-mock system)
        now (jt/local-date)]

    (testing "Should be able to init a system with PostgreSQL component"
      (is (match? {:common-test-clj.component.postgresql-mock/postgresql-mock #(= (type %) Pool)}
                  system)))

    (testing "Should be able to use the initiated PostgreSQL component to perform database operations"
      (is (= [{:apelido    "brunão"
               :nascimento now
               :nome       "nascimento"}]
             (pg.core/with-connection [conn pool]
               (pg/execute conn
                           "INSERT INTO pessoa (apelido, nome, nascimento) VALUES ($1, $2, $3)
                                                   returning *"
                           {:params ["brunão" "nascimento" now]})))))

    (testing "The System was stopped"
      (is (nil? (ig/halt! system))))))
