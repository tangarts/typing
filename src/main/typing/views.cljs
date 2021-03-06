(ns typing.views
  (:require
   [reagent.core :as r]
   [typing.state :as state]
   [typing.utils :refer [strip-text]]
   [typing.components.style :as style]
   [typing.components.text :refer [random-text]]
   [typing.components.character :refer [character]]
   [typing.components.timer :refer [timer-component display-time]]
   [clojure.string :as str]))


(defn get-word-type [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))


(defn render-text []
  (let [actual (r/atom (strip-text @state/input))]
    (into [:div {:style {:padding "36px 24px"}}
           [:div {:style style/inputs}]] 
        (for [[i c] (map-indexed vector @state/text)]
          ^{:key i}
          [character
           c
           (get @actual i)
           (get-word-type i (count @actual))]))))

(render-text)


(defn text-area []
  (fn []
    [:input#input
     {:style {:position "fixed" :left "-100%" :top "50%"} ; hidden
      :type "text"
      :auto-focus true
      :value @state/input
      :on-change #(reset! state/input (-> % .-target .-value))
      :disabled (if @state/finished? true false) 
;      :on-key-down #(swap! state/timer assoc :on? true) 
;     :on-key-press (fn [e] (.log js/console (.-key e)))
      }]))



(defn icon [name & body]
  [:i {:class (str "fa fa-lg fa-" name)
       :aria-hidden true} body])


(defn get-cpm
  []
  (Math/floor
    (/ (* (count @state/input) 60)
       (/ (- (:etime @state/timer)
             (:stime @state/timer)) 1000))))


(defn get-wpm [] (/ (get-cpm) 5))


(defn finished?  []
  (when (= @state/input (str/join @state/text))
    (swap! state/timer assoc :on? false)
    (swap! state/timer assoc :etime (.now js/Date))
    (reset! state/finished? true)
    [:div {:style {:margin-left "8px"}}
     "WPM: " (get-wpm)]))


(defn control-view []
  (fn []
    [:div 
     [:button
      {:on-click #((swap! state/timer merge (state/default-state))
                   (reset! state/text (strip-text (random-text)))
                   (reset! state/input "")
                   (reset! state/finished? false))
       :style style/button}
      [icon "refresh"]]]))


(defn nav []
  [:nav {:style {:position "absolute" :top 0 :right "32px" }}
   [:div [:p [:a {:href "#"} "nav"] ] ]])


(defn footer []
  [:footer {:style {:bottom 0 :position "absolute"}}
   [:p "made by "
    [:a {:href "https://tangarts.github.io/about"} "tangarts"] ]])


(defn container []
  [:section {:style style/container } 
   [:div {:style style/board}
    [:div {:style style/statistics}
     [timer-component]
     [:div {:style {:flex-grow "1"}}]
     [finished?]
     [control-view]]
    [render-text]]])


(defn app []
  (fn []
    [:div {:style style/root
           :on-click #(when-not @state/finished?
                        (-> js/document (.getElementById "input")
                            (.focus))) }
     ;     [nav]
     [text-area] ; hidden text-area
     [container]
     [footer]]))

