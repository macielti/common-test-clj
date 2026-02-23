(ns common-test-clj.helpers.schema-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.helpers.schema :as test.helper.schema]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [schema.core :as schema]
            [schema.test :as s])
  (:import (java.time Instant LocalDate LocalDateTime)
           (java.util Date)))

(schema/defschema SchemaTest
  {:a               schema/Str
   :b               schema/Keyword
   :date            Date
   :local-date-time LocalDateTime
   :local-date      LocalDate
   :instant         Instant})

(s/deftest generate-test
  (testing "Given a schema and overrides, we can generate a map that matches the schema"
    (is (match? {:a               "a"
                 :b               keyword?
                 :date            inst?
                 :local-date-time jt/local-date-time?
                 :local-date      jt/local-date?
                 :instant         jt/instant?}
                (test.helper.schema/generate SchemaTest {:a "a"} {})))))
