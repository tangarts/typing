(ns typing.views
  (:require
   [typing.state :as state]
   [typing.utils :refer [strip-text]]
   [typing.components.style :as style]
   [typing.components.text :refer [random-text]]
   [typing.components.character :refer [character get-character-state]]
   [typing.components.timer :refer [timer-component get-wpm]]
   [clojure.string :as str]))

(defn render-text []
 ; TODO: implement incorrect errors 
  ; (fn [])
  (into [:div {:style {:padding "36px 24px"}}
         [:div {:style style/inputs}]]
        (for [[i c] (map-indexed vector @state/text)]
          ^{:key i}
          [character
           c
           (get @state/input i)
           (get-character-state i (count @state/input))])))

; (->> (render-text) (flatten) vec)
; (:class (second (nth (render-text) 3)))
; (map second (render-text))

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

(defn finished?  []
  (when (= @state/input (str/join @state/text))
    (swap! state/timer assoc :on? false)
    (swap! state/timer assoc :etime (.now js/Date))
    (reset! state/finished? true)
    [:div {:style {:margin-left "8px"}}
     "WPM: " (get-wpm @state/input @state/timer)]))

(defn control-view []
  [:div
   [:button
    {:on-click #((swap! state/timer merge (state/default-state))
                 (reset! state/text (strip-text (random-text)))
                 (reset! state/input "")
                 (reset! state/finished? false))
     :style style/button}
    [icon "refresh"]]])

(defn footer []
  [:footer {:style {:bottom 0 :position "absolute"}}
   [:p "made by "
    [:a {:href "https://tangarts.github.io/about"} "tangarts"]]])

(defn container []
  [:section {:style style/container}
   [:div {:style style/board}
    [:div {:style style/statistics}
     [timer-component]
     [:div {:style {:flex-grow "1"}}]
     [finished?]
     [control-view]]
    [render-text]]])

(defn app []
  (fn []
    [:div
     {:style style/root
      :on-click
      #(when-not @state/finished?
         (-> js/document (.getElementById "input") (.focus)))}
     [text-area] ; hidden text-area
     [container]
     [footer]]))

