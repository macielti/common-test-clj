(ns common-test-clj.component.sqlite-mock
  (:require [clojure.tools.logging :as log]
            [migratus.core :as migratus]
            [integrant.core :as ig]
            [schema.core :as s]))

(defmethod ig/init-key ::sqlite-mock
  [_ _]
  (log/info :starting ::sqlite-mock)
  (let [database-name (str "/tmp/" (random-uuid) "-sqlite-mock.db")
        _ (migratus/migrate {:store         :database
                             :migration-dir "migrations-sqlite/"
                             :db            {:dbtype "sqlite"
                                             :dbname database-name}})]
    {:dbtype "sqlite"
     :dbname database-name}))

(defmethod ig/halt-key! ::sqlite-mock
  [_ _]
  (log/info :stopping ::sqlite-mock))

(s/defn sqlite-unit-mock
  "Intended to be used for unit testing"
  []
  (let [database-name (str "/tmp/" (random-uuid) "-sqlite-mock.db")
        _ (migratus/migrate {:store         :database
                             :migration-dir "migrations-sqlite/"
                             :db            {:dbtype "sqlite"
                                             :dbname database-name}})]
    {:dbtype "sqlite"
     :dbname database-name}))
