(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.components.style :as style]
   [typing.components.character :refer [character]]
   [typing.state :as state]
   [typing.utils :refer [strip-text]]
   [typing.components.text :refer [random-text]]
   [typing.components.timer :refer [timer-component]]
   [clojure.string :as string]))


(defn default-state []
  "Generates a map with start/end time keys"
  (let [now (.now js/Date)]
    {:stime now
     :etime nil
     :on? true}))


(defn get-word-type [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))

(defn render-text []
  (let [actual (r/atom (string/split @state/input #""))]
    (into [:div ]
        (for [[i c] (map-indexed vector @state/text)]
          ^{:key i}
          [character
           c
           (get @actual i)
           (get-word-type i (count @actual))]))))



(defn text-area []
  (fn []
    [:input#input
     {:style {:position "fixed" :left "-100%" :top "50%"} ; hidden
      :type "text"
      :auto-focus "true"
      :value @state/input
      :on-change #(reset! state/input (-> % .-target .-value))
      ;:on-key-down (fn [e] (.log js/console (.-key e)))
      }]))


(defn finished?  []
  (when (= @state/input (string/join @state/text))
    ((reset! state/text (strip-text (random-text)))
      (reset! state/input nil))
    ; (swap! (:etime state/timer (.now js/Date)))
    ))

(defn icon [name & body]
  [:i {:class (str "fa fa-lg fa-" name)
       :aria-hidden true} body])

(defn get-cpm
  []
  (/ (Math/floor (* (count @state/input) 60))
     (/ (- (.now js/Date) (:stime @state/timer)) 1000)))

(defn get-wpm [] (/ (get-cpm) 5))

(defn control-view []
  (fn []
    [:div 
     [:button {:on-click
               #((swap! state/timer merge (default-state))
                 (reset! state/text (strip-text (random-text)))
                 (reset! state/input nil))
               :style style/button}
      [icon "refresh"]]]))

(defn app [] 
  (fn []
      [:main {:style style/root
             :on-click #(-> js/document
                            (.getElementById "input") (.focus))}
       [:div {:style style/container} 
        [:div {:style style/board}
         [:div 
          [:div {:style style/statistics}

           [timer-component]
           [:div {:style {:margin-left "8px"}}
            " CPM: " ] ; fix

           [:div {:style {:flex-grow "1"}}]

           [finished?]
           [control-view]
           ]]
         [:div {:style {:padding "35px 25px"}}
          [:div {:style style/inputs}
           [text-area] ; hidden text-area

           [render-text]

           ]]]]]))

