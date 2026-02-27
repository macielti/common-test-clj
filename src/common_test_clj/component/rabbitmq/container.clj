(ns common-test-clj.component.rabbitmq.container
  (:require [clojure.tools.logging :as log]
            [integrant.core :as ig])
  (:import (org.testcontainers.containers RabbitMQContainer)))

(defmethod ig/init-key ::rabbitmq-container
  [_ _components]
  (log/info :starting ::rabbitmq-container)
  (let [rabbitmq-container (doto (RabbitMQContainer. "rabbitmq:3-management-alpine") .start)]
    {:url       (.getAmqpUrl rabbitmq-container)
     :container rabbitmq-container}))

(defmethod ig/halt-key! ::rabbitmq-container
  [_ component]
  (log/info :stopping ::rabbitmq-container)
  (.stop ^RabbitMQContainer (:container component)))
