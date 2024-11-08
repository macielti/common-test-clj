(ns common-test-clj.helpers.schema
  (:require [clojure.test.check.generators :as test.check.generators]
            [java-time.api :as jt]
            [schema-generators.complete :as c]
            [schema.core :as s])
  (:import (java.time LocalDate LocalDateTime)
           (java.util Date)))

(def leaf-generators
  {LocalDateTime (test.check.generators/fmap #(jt/local-date-time %) (test.check.generators/choose 2000 2024))
   LocalDate     (test.check.generators/fmap #(jt/local-date %) (test.check.generators/choose 2000 2024))
   Date          (test.check.generators/fmap #(jt/java-date %) (test.check.generators/choose 2000 2024))
   BigDecimal    (test.check.generators/fmap #(bigdec %) (test.check.generators/choose 0 1000))})

(s/defn generate :- s/Any
  [schema :- s/Any
   overrides :- (s/pred map?)]
  (c/complete overrides
              schema
              {}
              leaf-generators))
