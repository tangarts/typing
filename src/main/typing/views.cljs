(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.components.style :as style]
   [typing.components.word :refer [character-]]
   [typing.state :as state]
   [typing.utils :refer [strip-text random-text]]
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


(defn render-text [text actual]
  (into [:div ]
        (for [[i c] (map-indexed vector text)]
          ^{:key i}
          [character-
           c
           (get @actual i)
           (get-word-type i (count @actual))])))



(defn text-area [input-ref!]
  (fn []
    [:input#input
     {:style {:position "fixed" :left "-100%" :top "50%"} ; hidden
      :type "text"
      :auto-focus "true"
      :value @input-ref!
      :on-change #(reset! input-ref! (-> % .-target .-value))
      ;:on-key-down (fn [e] (.log js/console (.-key e)))
      }]))


(defn finished?
  [text input-ref!]
  (when (>= (inc (count @input-ref!)) (count @text))
    [:p "Done"]))

(defn icon [name & body]
  [:i {:class (str "fa fa-lg fa-" name)
       :aria-hidden true} body])

(defn control-view []
  (fn []
    [:div 
     [:button {:on-click
               #((swap! state/timer merge (default-state))
                 (reset! state/text (strip-text (random-text)))
                 (reset! state/input nil)
                 
                 ; (swap! state/timer)
                         )
               :style style/button}
      [icon "refresh"]]]))

(defn app [] 
  (let [input-ref! state/input
        text state/text]
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

           [control-view]
           ]]
         [:div {:style {:padding "35px 25px"}}
          [:div {:style style/inputs}
           [text-area input-ref!] ; hidden text-area

           [render-text @text (r/atom (string/split @input-ref! #""))]

           [finished? text input-ref!]
           ]]]]])))

