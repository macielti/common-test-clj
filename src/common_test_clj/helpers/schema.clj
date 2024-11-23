(ns common-test-clj.helpers.schema
  (:require [clojure.string :as str]
            [clojure.test.check.generators :as test.check.generators]
            [common-clj.schema.extensions :as schema.extensions]
            [java-time.api :as jt]
            [schema-generators.complete :as c]
            [schema.core :as s])
  (:import (java.time LocalDate LocalDateTime)
           (java.util Date)))

(def leaf-generators
  {LocalDateTime                       (test.check.generators/fmap #(jt/local-date-time %) (test.check.generators/choose 2000 2024))
   LocalDate                           (test.check.generators/fmap #(jt/local-date %) (test.check.generators/choose 2000 2024))
   Date                                (test.check.generators/fmap #(jt/java-date %) (test.check.generators/choose 2000 2024))
   BigDecimal                          (test.check.generators/fmap #(bigdec %) (test.check.generators/choose 0 1000))
   schema.extensions/LocalDateWire     (test.check.generators/fmap #(-> (jt/local-date %) str (str/split #"T") first) (test.check.generators/choose 2000 2024))
   schema.extensions/LocalDateTimeWire (test.check.generators/fmap #(-> (jt/local-date-time %) str) (test.check.generators/choose 2000 2024))
   schema.extensions/UuidWire          (test.check.generators/fmap str test.check.generators/uuid)})

(s/defn generate :- s/Any
  [schema :- s/Any
   overrides :- (s/pred map?)]
  (c/complete overrides
              schema
              {}
              leaf-generators))
