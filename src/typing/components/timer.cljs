(ns typing.components.timer
  (:require 
    [reagent.core :as r]
    [typing.state :as state]
    [clojure.string :as str]))

(extend-type number
  ICloneable
  (-clone [n] (js/Number. n)))

(extend-type boolean
  ICloneable
  (-clone [b] (js/Boolean. b)))


(defn now [] (.now js/Date))

(defn ->date
  "Creates a date object"
  ([] (->date (now)))
  ([t] (js/Date. t)))

(defn format-time
  "Format time as min:sec"
  [d]
  (let [min (.getMinutes d)
        sec (.getSeconds d)
        formatted (map #(if (< % 10) (str "0" %) %) [min sec])]
    (str/join ":" formatted)))

;; Converts numbers to dates to be passed to `format-time`
(defmulti display-time (fn [d] (type d)))
(defmethod display-time js/Number [t] (format-time (->date t)))
(defmethod display-time js/Date [d] (format-time d))


(defn get-state
  ([key & keys]
     (map get-state (conj keys key)))
  ([key]
     (get-in @state/timer [key])))

(defn on? [] (get-state :on?))

(defn set-state!
  ([val-map]
     (swap! state/timer merge val-map))
  ([key val]
     (swap! state/timer assoc key val)))

(defn timer-component []
  (let [start (:stime @state/timer)
        timer (r/atom (- (.now js/Date) 
                         (:stime @state/timer)))]
    (fn []
      (js/setInterval
        #(when (on?)
          (reset! timer (- (now) start)))
       1000)
      [:div (display-time @timer)])))
