(defproject net.clojars.macielti/common-test-clj "5.2.4"

  :description "Common utilities for testing Clojure code"

  :url "https://github.com/macielti/common-test-clj"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.12.0"]
                 [net.clojars.macielti/common-clj "43.74.74"]
                 [org.testcontainers/postgresql "1.20.4"]
                 [com.github.igrishaev/pg2-core "0.1.33"]
                 [prismatic/schema-generators "0.1.5"]
                 [org.clojure/tools.logging "1.3.0"]
                 [org.clojure/test.check "1.1.1"]
                 [clojure.java-time "1.4.3"]
                 [prismatic/schema "1.4.1"]
                 [integrant "0.13.1"]
                 [migratus "1.6.3"]
                 [diehard "0.11.12"]

                 [org.xerial/sqlite-jdbc "3.41.2.1"]
                 [com.github.seancorfield/next.jdbc "1.3.994"]]

  :profiles {:dev {:plugins        [[com.github.clojure-lsp/lein-clojure-lsp "1.4.16"]
                                    [com.github.liquidz/antq "RELEASE"]]

                   :resource-paths ^:replace ["test/resources"]

                   :test-paths     ^:replace ["test/unit" "test/integration" "test/helpers"]

                   :dependencies   [[hashp "0.2.2"]
                                    [nubank/matcher-combinators "3.9.1"]]

                   :injections     [(require 'hashp.core)]

                   :aliases        {"clean-ns"     ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
                                    "format"       ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
                                    "diagnostics"  ["clojure-lsp" "diagnostics"]
                                    "lint"         ["do" ["clean-ns"] ["format"] ["diagnostics"]]
                                    "clean-ns-fix" ["clojure-lsp" "clean-ns"]
                                    "format-fix"   ["clojure-lsp" "format"]
                                    "lint-fix"     ["do" ["clean-ns-fix"] ["format-fix"]]
                                    "outdated"     ["with-profile" "antq" "run" "-m" "antq.core"]}}})
