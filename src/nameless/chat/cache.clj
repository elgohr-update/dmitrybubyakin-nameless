(ns nameless.chat.cache
  (:require [taoensso.carmine :as car :refer (wcar)]
            [mount.core :refer [defstate]]
            [config.core :refer [env]]))

(def session-key "session-")
(def conn {:pool {} :spec {:host (:host (:cache env)) :port (:port (:cache env))}})

(defmacro wcar* [& body] `(car/wcar conn ~@body))

(defn save-session
  [uid start-time]
  (wcar* (car/set (str session-key uid) start-time)))

(defn get-session
  [uid]
  (wcar* (car/get uid)))

(defn active-sessions []
  (wcar* (car/keys (str session-key "*"))))
