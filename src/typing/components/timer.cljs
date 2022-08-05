(ns typing.components.timer
  (:require
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

(defn timer-component [state]
  (fn [state]
    (js/setInterval
     (fn []
       (when (and (not (:finished state)) (get-in state [:history 0 :ts]))
         (reset! (:timer state) (- (now) (get-in state [:history 0 :ts]))))) 1000))
    [:div (display-time (:timer state))])

(defn cpm
  "calculate characters per minute
  - ^String input   : string of characters
  - ^Integer start  : start time (ms)
  - ^String input   : end time (ms)
  "
  [input start end]
  (/ (* (count input) 60)
     (/ (- end
           start) 1000)))

(defn wpm
  "words per minute"
  [input start end]
  (/ (cpm input start end) 5))

(defn rolling-avg-speed 
  [timestamps]
  (if (> (->> timestamps count dec) 5)
    (let [ts (subvec timestamps 0 5)]
    (wpm ts
         (ts 0)
         (ts (->> ts count dec))))
  (wpm timestamps
         (timestamps 0)
         (timestamps (->> timestamps count dec)))))
