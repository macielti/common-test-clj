(ns common-test-clj.helpers.schema-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.helpers.schema :as test.helper.schema]
            [java-time.api :as jt]
            [matcher-combinators.test :refer [match?]]
            [schema.core :as s]
            [schema.test])
  (:import (java.time Instant LocalDate LocalDateTime)
           (java.util Date)))

(s/defschema SchemaTest
  {:a               s/Str
   :b               s/Keyword
   :date            Date
   :local-date-time LocalDateTime
   :local-date      LocalDate
   :instant         Instant
   :inst-schema     s/Inst})

(schema.test/deftest generate-test
  (testing "Given a schema and overrides, we can generate a map that matches the schema"
    (is (match? {:a               "a"
                 :b               keyword?
                 :date            inst?
                 :local-date-time jt/local-date-time?
                 :local-date      jt/local-date?
                 :instant         jt/instant?
                 :inst-schema     inst?}
                (test.helper.schema/generate SchemaTest {:a "a"} {})))))
