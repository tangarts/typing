(ns typing.state 
  (:require 
    [reagent.core :as r]
    [typing.utils :refer [strip-text]]
    [typing.components.text :refer [random-text]]))


(def finished? (r/atom false))

(def history (r/atom []))

(def text (r/atom (strip-text (random-text))))

(def input (r/atom ""))

(defn default-state []
  (let [now (.now js/Date)]
    {:stime now
     :etime nil
     :on? true}))

(def timer (r/atom (default-state)))


; (defn set-value! [id value]
;   (swap! state assoc :saved? false))

; (defn get-value [id]
;   (get @state :key id))

