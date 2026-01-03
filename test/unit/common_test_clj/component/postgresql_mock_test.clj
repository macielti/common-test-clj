(ns common-test-clj.component.postgresql-mock-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.component.postgresql-mock :as component.postgresql-mock]
            [schema.test :as s])
  (:import (org.pg Pool)))

(s/deftest postgresql-pool-mock-test
  (testing "Creating a mocked PostgreSQL connection"
    (let [conn (component.postgresql-mock/postgresql-pool-mock "resources/migrations")]
      (is (= (type conn) Pool)))))
