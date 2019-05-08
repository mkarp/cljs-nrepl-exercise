(ns cljs-test.main
  (:require [nrepl.server :as nrepl-server]
            [rebel-readline.clojure.main :as rebel-clj-main]
            [rebel-readline.core :as rebel-core]))

(use 'clojure.repl)

(defonce nrepl-server (atom nil))

(defn nrepl-handler []
  (require 'cider.nrepl)
  (ns-resolve 'cider.nrepl 'cider-nrepl-handler))

(defn middleware []
  (require 'cider.piggieback)
  [(ns-resolve 'cider.piggieback 'wrap-cljs-repl)])

(defn start-nrepl! []
  (reset! nrepl-server
          (nrepl-server/start-server :port 7888
                                     :handler (nrepl-handler)
                                     :middleware (middleware))))

(defn stop-nrepl! []
  (when (not (nil? @nrepl-server))
    (nrepl-server/stop-server @nrepl-server)
    (reset! nrepl-server nil)))

(defn fig-start [build]
  (require 'figwheel.main.api)
  ((ns-resolve 'figwheel.main.api 'start) build))

(defn fig-stop-all []
  (require 'figwheel.main.api)
  ((ns-resolve 'figwheel.main.api 'stop-all)))

(defn fig []
  (fig-stop-all)
  (fig-start "build"))

(defn -main []
  (rebel-core/ensure-terminal
   (rebel-clj-main/repl*
    {:init (fn []
             (require '[cljs-test.main :refer :all]))})))
