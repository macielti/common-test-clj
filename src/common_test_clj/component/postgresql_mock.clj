(ns common-test-clj.component.postgresql-mock
  (:require [clojure.tools.logging :as log]
            [diehard.core :as dh]
            [integrant.core :as ig]
            [pg.core :as pg]
            [pg.migration.core :as mig]
            [pg.pool :as pool]
            [schema.core :as s])
  (:import (org.pg.error PGError)
           (org.testcontainers.containers PostgreSQLContainer)))

(defmethod ig/init-key ::postgresql-mock
  [_ _]
  (log/info :starting ::postgresql-mock)
  (let [postgresql-container (doto (PostgreSQLContainer. "postgres:16-alpine") .start)
        postgresql-config {:host     (.getHost postgresql-container)
                           :port     (.getMappedPort postgresql-container PostgreSQLContainer/POSTGRESQL_PORT)
                           :user     (.getUsername postgresql-container)
                           :password (.getPassword postgresql-container)
                           :database (.getDatabaseName postgresql-container)}
        pool (dh/with-retry {:retry-on    PGError
                             :delay-ms    2000
                             :max-retries 3}
               (pool/pool postgresql-config))]
    (mig/migrate-all postgresql-config)
    pool))

(defmethod ig/halt-key! ::postgresql-mock
  [_ pool]
  (log/info :stopping ::postgresql-mock)
  (pool/close pool))

(s/defn postgresql-conn-mock
  "Intended to be used for unit testing"
  []
  (let [postgresql-container (doto (PostgreSQLContainer. "postgres:16-alpine")
                               .start)
        postgresql-config {:host     (.getHost postgresql-container)
                           :port     (.getMappedPort postgresql-container PostgreSQLContainer/POSTGRESQL_PORT)
                           :user     (.getUsername postgresql-container)
                           :password (.getPassword postgresql-container)
                           :database (.getDatabaseName postgresql-container)}]
    (dh/with-retry {:retry-on    PGError
                    :delay-ms    2000
                    :max-retries 3}
      (mig/migrate-all postgresql-config))
    (pg/connect postgresql-config)))
