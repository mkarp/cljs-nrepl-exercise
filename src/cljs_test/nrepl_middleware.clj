(ns cljs-test.nrepl-middleware
  (:require [cider.piggieback :as piggieback]
            [nrepl.middleware :refer [set-descriptor!]]))

(defn wrap-app-reload [reload-opts]
  (fn [handler]
    (fn [{:keys [session] :as msg}]
      (if (and session
               (not (string? session))
               (@session #'piggieback/*cljs-repl-env*)
               (= (:op msg) "eval"))
        (handler (assoc msg :code
                        (str "(let [result " (:code msg) "] "
                             "(" (str (:ns reload-opts) "/" (:fn reload-opts)) ")) "
                             "result)")))
        (handler msg)))))

(set-descriptor! #'wrap-app-reload
                 {:requires #{"session"}
                  :expects #{#'piggieback/wrap-cljs-repl}})
