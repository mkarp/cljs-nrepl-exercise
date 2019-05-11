(ns cljs-test.main
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [nrepl.misc :refer [uuid]]
            [nrepl.server :as nrepl-server]
            [rebel-readline.clojure.main :as rebel-clj-main]
            [rebel-readline.core :as rebel-core]))

(def nrepl-port 7888)
(defonce nrepl-server (atom nil))

(defn log [& msgs]
  (println (apply str (cons "[cljs-test] " msgs))))

(defn wrap-app-reload [handler]
  (fn [msg]
    (handler msg)
    (when (and (= (:op msg) "eval")
               (s/ends-with? (str (:file msg)) "cljs"))
      (log "Eval and reload " (:ns msg))
      (handler {:transport (:transport msg)
                :session (:session msg)
                :ns "cljs-test.main"
                :op "eval"
                :id (uuid)
                :code "(cljs-test.main/reload)"}))))

(defn nrepl-handler []
  (require 'cider.nrepl
           'cider.piggieback)
  (apply nrepl.server/default-handler
         (conj
          (mapv resolve @(ns-resolve 'cider.nrepl 'cider-middleware))
          (ns-resolve 'cider.piggieback 'wrap-cljs-repl)
          (ns-resolve 'cljs-test.main 'wrap-app-reload))))

(defn start-nrepl! []
  (reset! nrepl-server
          (nrepl-server/start-server :port nrepl-port
                                     :handler (nrepl-handler)))
  (log "nREPL server started on port " nrepl-port)
  (spit ".nrepl-port" nrepl-port))

(defn stop-nrepl! []
  (when (not (nil? @nrepl-server))
    (nrepl-server/stop-server @nrepl-server)
    (reset! nrepl-server nil)
    (log "nREPL server on port " nrepl-port " stopped")
    (io/delete-file ".nrepl-port" true)))

(defn start-fig
  ([]
   (start-fig "build"))
  ([build]
   (require 'figwheel.main.api)
   ((ns-resolve 'figwheel.main.api 'start) build)))

(defn stop-fig []
  (require 'figwheel.main.api)
  ((ns-resolve 'figwheel.main.api 'stop-all)))

(defn reset-fig []
  (stop-fig)
  (start-fig "build"))

(defn cljs-repl
  ([]
   (cljs-repl "build"))
  ([build]
   (require 'figwheel.main.api)
   ((ns-resolve 'figwheel.main.api 'cljs-repl) build)))

(defn -main []
  (rebel-core/ensure-terminal
   (rebel-clj-main/repl*
    {:init (fn []
             (require '[cljs-test.main :refer :all])
             (use 'clojure.repl)
             (start-nrepl!)
             (start-fig))})))
