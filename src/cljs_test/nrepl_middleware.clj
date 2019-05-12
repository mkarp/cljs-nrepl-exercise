(ns cljs-test.nrepl-middleware
  (:require [clojure.string :as s]
            [nrepl.misc :refer [uuid]]
            [cljs-test.logging :refer [log]]))

(defn wrap-app-reload [reload-opts]
  (fn [handler]
    (fn [msg]
      (handler msg)
      (when (and (= (:op msg) "eval")
                 (s/ends-with? (str (:file msg)) "cljs"))
        (log "Eval and reload " (:ns msg))
        (handler {:transport (:transport msg)
                  :session (:session msg)
                  :ns (:ns reload-opts)
                  :op "eval"
                  :id (uuid)
                  :code (str "(" (:ns reload-opts) "/" (:fn reload-opts) ")")})))))
