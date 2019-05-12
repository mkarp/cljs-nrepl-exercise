(ns cljs-test.nrepl-middleware
  (:require [clojure.string :as s]
            [nrepl.misc :refer [uuid]]
            [cljs-test.logging :refer [log]]))

(defn wrap-app-reload [reload-opts]
  (fn [handler]
    (fn [msg]
      (if (and (= (:op msg) "eval")
               (s/ends-with? (str (:file msg)) "cljs"))
        (do
          (log "Eval and reload" (:ns msg))
          (handler {:transport (:transport msg)
                    :session (:session msg)
                    :ns (:ns reload-opts)
                    :op "eval"
                    :id (uuid)
                    :code (str "(let [result " (:code msg) "] (" (:ns reload-opts) "/" (:fn reload-opts) ") result)")}))
        (handler msg)))))
