(ns rabbitmq-container-test
  (:require [clojure.test :refer [is testing]]
            [common-test-clj.component.rabbitmq.container :as component.rabbitmq.container]
            [hashp.core]
            [integrant.core :as ig]
            [langohr.channel :as lch]
            [langohr.core :as rmq]
            [schema.test :as s])
  (:import (com.novemberain.langohr Connection)
           (com.rabbitmq.client.impl.recovery AutorecoveringChannel)))

(def system-setup {::component.rabbitmq.container/rabbitmq-container {:component {}}})

(s/deftest postgresql-mock-component-test
  (let [system (ig/init system-setup)
        uri (-> (::component.rabbitmq.container/rabbitmq-container system) :url)
        connection (rmq/connect {:uri uri})
        channel (lch/open connection)]

    (testing "The System was started"
      (is (string? uri))

      (is (= Connection
             (type connection)))

      (is (= AutorecoveringChannel
             (type channel))))

    (testing "The System was stopped"
      (is (nil? (ig/halt! system))))))
