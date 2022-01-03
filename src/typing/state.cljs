(ns typing.state
  (:require
   [reagent.core :as r]
   [typing.components.text :refer [data]]))

(def finished (r/atom false))

(def history (r/atom []))

(def text (r/atom (rand-nth data)))

(def input (r/atom ""))

(def timer (r/atom 0))

; (defn set-value! [id value]
;   (swap! state assoc :saved? false))

; (defn get-value [id]
;   (get @state :key id))

