(ns typing.components.timer
  (:require 
    [reagent.core :as r]
    [clojure.string :as string]))

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
    (string/join ":" formatted)))

;; Converts numbers to dates to be passed to `format-time`
(defmulti display-time (fn [d] (type d)))
(defmethod display-time js/Number [t] (format-time (->date t)))
(defmethod display-time js/Date [d] (format-time d))

(defn time-diff [start end] (- end start))

(defn default-state []
  "Generates a map with start/end time keys"
  (let [now (now)]
    {:stime now
     :etime nil
     :on? true}))


(def state (r/atom (default-state)))

(def history (r/atom []))

(defn get-state
  ([key & keys]
     (map get-state (conj keys key)))
  ([key]
     (get-in @state [key])))

(defn set-state!
  ([val-map]
     (swap! state merge val-map))
  ([key val]
     (swap! state assoc key val)))

(defn on? [] (get-state :on?))


(defn timer-component []
  (let [start (:stime @state)
        timer (r/atom (time-diff (:stime @state) (.now js/Date))) ]
    (fn []
      (js/setInterval
        #(when (on?) (reset! timer (time-diff (:stime @state) (now))))
       1000)
      [:div "timer: " (display-time @timer)])))


(defn btn
  "Creates a large button"
  ([body] (btn {} body))

  ([{:keys [disabled?] :as opts} & body]
     [:button (merge {:class (str "btn")
                      :disabled disabled?}
                     (select-keys opts [:on-click]))
      body]))

(defn icon [name]
  [:i {:class (str "fa fa-" name)} name])

(defn controls-view []
  (let [on-toggle #(set-state! :on? (not (on?)))
        on-reset #(when-not (on?)
                    (set-state! (default-state)))]

    [:div {:class "buttons"}
     [btn {:on-click on-toggle} [icon (if (on?) "pause" "play")]]
     [btn {:on-click on-reset :disabled? (on?)} [icon "refresh"]]]))


