(ns user
  (:require [nrepl.server :as nrepl-server]
            [clojure.java.io :as io]))

(use 'clojure.repl)

(defonce nrepl-server (atom nil))

(defn nrepl-handler []
  (require 'cider.nrepl)
  (ns-resolve 'cider.nrepl 'cider-nrepl-handler))

(defn start-nrepl! []
  (reset! nrepl-server (nrepl-server/start-server :port 7888 :handler (nrepl-handler))))

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
