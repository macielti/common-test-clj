(ns common-test-clj.component.postgresql-mock-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.component.postgresql-mock :as component.postgresql-mock]
            [schema.test :as s])
  (:import (org.pg Connection)))

(s/deftest postgresql-conn-mock-test
  (testing "Creating a mocked PostgreSQL connection"
    (let [conn (component.postgresql-mock/postgresql-conn-mock)]
      (is (= (type conn) Connection)))))
