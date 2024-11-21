(ns common-test-clj.component.sqlite-mock
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig]
            [next.jdbc :as jdbc]
            [schema.core :as s]))

(defmethod ig/init-key ::sqlite-mock
  [_ {:keys [schemas]}]
  (log/info :starting ::sqlite-mock)
  (let [database-name (str "/tmp/" (random-uuid) "-sqlite-mock.db")
        sqlite {:dbtype "sqlite"
                :dbname database-name}]
    (with-open [conn (jdbc/get-connection sqlite)]
      (doseq [schema schemas]
        (try (jdbc/execute! conn [schema])
             (catch Exception e
               (log/error (ex-message e))))))
    sqlite))

(defmethod ig/halt-key! ::sqlite-mock
  [_ _]
  (log/info :stopping ::sqlite-mock))

(s/defn sqlite-unit-mock
  "Intended to be used for unit testing"
  [schemas]
  (let [database-name (str "/tmp/" (random-uuid) "-sqlite-mock.db")
        sqlite {:dbtype "sqlite"
                :dbname database-name}]
    (with-open [conn (jdbc/get-connection sqlite)]
      (doseq [schema schemas]
        (try (jdbc/execute! conn [schema])
             (catch Exception e
               (log/error (ex-message e))))))
    sqlite))
