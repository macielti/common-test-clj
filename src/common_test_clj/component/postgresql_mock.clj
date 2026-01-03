(ns common-test-clj.component.postgresql-mock
  (:require [clojure.tools.logging :as log]
            [diehard.core :as dh]
            [integrant.core :as ig]
            [pg.core :as pg]
            [schema.core :as s]
            [pg.migration.core :as migrations])
  (:import (org.pg.error PGError)
           (org.testcontainers.containers PostgreSQLContainer)))

(defmethod ig/init-key ::postgresql-mock
  [_ {:keys [components]}]
  (log/info :starting ::postgresql-mock)
  (let [postgresql-container (doto (PostgreSQLContainer. "postgres:16-alpine") .start)
        postgresql-config {:host     (.getHost postgresql-container)
                           :port     (.getMappedPort postgresql-container PostgreSQLContainer/POSTGRESQL_PORT)
                           :user     (.getUsername postgresql-container)
                           :password (.getPassword postgresql-container)
                           :database (.getDatabaseName postgresql-container)}
        migrations-config (-> components :config :postgresql-migrations (merge postgresql-config))]
    (dh/with-retry {:retry-on    PGError
                    :delay-ms    2000
                    :max-retries 3}
                   (migrations/migrate-all migrations-config))
    (pg/pool postgresql-config)))

(defmethod ig/halt-key! ::postgresql-mock
  [_ pool]
  (log/info :stopping ::postgresql-mock)
  (pg/close pool))

(s/defn postgresql-pool-mock
  "Intended to be used for unit testing"
  [migrations-path]
  (let [postgresql-container (doto (PostgreSQLContainer. "postgres:16-alpine") .start)
        postgresql-config {:host     (.getHost postgresql-container)
                           :port     (.getMappedPort postgresql-container PostgreSQLContainer/POSTGRESQL_PORT)
                           :user     (.getUsername postgresql-container)
                           :password (.getPassword postgresql-container)
                           :database (.getDatabaseName postgresql-container)}
        migrations-config (merge postgresql-config {:migrations-table :migrations
                                                    :migrations-path  migrations-path})]
    (dh/with-retry {:retry-on    PGError
                    :delay-ms    2000
                    :max-retries 3}
                   (migrations/migrate-all migrations-config))
    (pg/pool postgresql-config)))
