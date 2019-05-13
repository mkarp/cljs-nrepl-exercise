(ns cljs-test.nrepl-middleware
  (:require [clojure.string :as s]
            [cljs-test.logging :refer [log]]))

(defn wrap-app-reload [reload-opts]
  (fn [handler]
    (fn [msg]
      (if (and (= (:op msg) "eval")
               (s/ends-with? (str (:file msg)) "cljs"))
        (do
          (log "Eval and reload" (:ns msg))
          (handler (assoc msg :code (str "(let [result " (:code msg) "] (" (:ns reload-opts) "/" (:fn reload-opts) ") result)"))))
        (handler msg)))))
