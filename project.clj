(defproject net.clojars.macielti/common-test-clj "7.0.2"

  :description "Common utilities for testing Clojure code"

  :url "https://github.com/macielti/common-test-clj"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.12.4"]
                 [net.clojars.macielti/common-clj "44.2.0"]
                 [org.testcontainers/postgresql "1.21.4"]
                 [com.github.igrishaev/pg2-core "0.1.42"]
                 [prismatic/schema-generators "0.1.5"]
                 [org.clojure/tools.logging "1.3.1"]
                 [org.clojure/test.check "1.1.3"]
                 [clojure.java-time "1.4.3"]
                 [prismatic/schema "1.4.1"]
                 [integrant "1.0.1"]
                 [migratus "1.6.5"]
                 [diehard "0.12.0"]
                 [com.github.igrishaev/pg2-migration "0.1.42"]

                 [org.xerial/sqlite-jdbc "3.51.1.0"]
                 [com.github.seancorfield/next.jdbc "1.3.1086"]]

  :profiles {:dev {:plugins        [[com.github.clojure-lsp/lein-clojure-lsp "2.0.13"]
                                    [com.github.liquidz/antq "RELEASE"]]

                   :resource-paths ["test/resources"]

                   :test-paths     ^:replace ["test/unit" "test/integration" "test/helpers"]

                   :dependencies   [[hashp "0.2.2"]
                                    [nubank/matcher-combinators "3.10.0"]]

                   :injections     [(require 'hashp.core)]

                   :aliases        {"clean-ns"     ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
                                    "format"       ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
                                    "diagnostics"  ["clojure-lsp" "diagnostics"]
                                    "lint"         ["do" ["clean-ns"] ["format"] ["diagnostics"]]
                                    "clean-ns-fix" ["clojure-lsp" "clean-ns"]
                                    "format-fix"   ["clojure-lsp" "format"]
                                    "lint-fix"     ["do" ["clean-ns-fix"] ["format-fix"]]
                                    "outdated"     ["with-profile" "antq" "run" "-m" "antq.core"]}}})
