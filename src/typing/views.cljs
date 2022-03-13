(ns typing.views
  (:require
   [typing.state :as state]
   [typing.components.style :as style]
   [typing.components.text :refer [data]]
   [typing.components.timer :refer [wpm]]))

(enable-console-print!)

(defn first-mistake
  [s1 s2]
  (first
   (for [[i v] (map-indexed vector s1)
         :when (not= (get s2 i) v)]
     i)))

(defn character
  [index c mistake n]
  (cond
    (> index n) {:char c :status :untyped}
    (= index n) {:char c :status :current}
    (and mistake (>= index mistake)) {:char c :status :wrong}
    :else {:char c :status :correct}))

(defn characters
  [input text]
  (vec
   (let [mistake (first-mistake input text)
         n (count input)]
     (for [[index c] (map-indexed vector text)]
       (character index c mistake n)))))

(defn mistyped-index
  [history]
  (for [[i v] (map-indexed vector history)
        :when (= "Backspace" (:key v))]
    i))

(defn render-text []
 ; TODO: implement incorrect errors 
  (fn []
    (into [:div {:style {:padding "36px"}}
           [:div {:style style/inputs}]]
          (for [c (characters @state/input (:text @state/text))]
            [:span {:style style/word-css
                    :class [(case (:status c)
                              :correct "correct"
                              :wrong "incorrect"
                              :untyped "awaiting"
                              :current "cursor"
                              "")]} (:char c)]))))
; (character
;            c
;            (get-character-state i (count @state/input)))

(defn text-area []
  (fn []
    [:input#input
     {:style {:position "fixed" :left "-100%" :top "50%"} ; hidden
      :type "text"
      :auto-focus true
      :value @state/input
      :on-change #(reset! state/input (-> % .-target .-value))
      :disabled (if @state/finished true false)
      ;  :on-key-down  
      :on-key-down (fn [e]  (swap! state/history
                                   conj {:key (.-key e)
                                         :ts (.now js/Date)}))}]))

(defn icon [name & body]
  [:i {:class (str "fa fa-lg fa-" name)
       :aria-hidden true} body])

(defn finished?  []
  (when (= @state/input (:text @state/text))
    (reset! state/finished true)
    [:div {:style {:margin-left "8px"}}
     "WPM: "
     (Math/floor (wpm @state/input
                      ((@state/history 0) :ts)
                      ((@state/history (->> @state/history count dec)) :ts)))]))

(defn control-view []
  [:div
   [:button
    {:on-click (fn []
                 (reset! state/text (rand-nth data))
                 (reset! state/input "")
                 (reset! state/history [])
                 (reset! state/timer 0)
                 (reset! state/finished false))
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
     [:div {:style {:flex-grow "1"}}]
     [finished?]
     [control-view]]
    [render-text]]])

(defn app []
  (fn []
    [:div
     {:style style/root
      :on-click
      #(when-not @state/finished
         (-> js/document (.getElementById "input") (.focus)))}
     [text-area] ; hidden text-area
     [container]

     [footer]]))
