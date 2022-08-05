(ns typing.views
  (:require
   [typing.state :as state]
   [typing.components.style :as style]
   [typing.components.text :refer [data]]
   [typing.components.timer :refer [wpm]]
   [typing.utils.localstorage :as ls]))

(enable-console-print!)

(defn character
  [^number index c mistake n]
  (cond
    (> index n) {:char c :status :untyped}
    (= index n) {:char c :status :current}
    (and mistake (>= index mistake)) {:char c :status :wrong}
    :else {:char c :status :correct}))

(defn first-mistake
  [s1 s2]
  (first
   (for [[i v] (map-indexed vector s1)
         :when (not= (get s2 i) v)]
     i)))
(defn characters
  [input text]
  (vec
   (let [mistake (first-mistake input text)
         n (count input)]
     (for [[index c] (map-indexed vector text)]
       (character index c mistake n)))))

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
      :on-key-down 
      (fn [e] (swap! state/history
                     conj {:key (.-key e)
                           :ts (.now js/Date)
                           :index (count @state/input)}))}]))

(defn icon [name & body]
  [:i {:class (str "fa fa-lg fa-" name)
       :aria-hidden true} body])

(defn _session []
  (when (= @state/input (:text @state/text))
    (let [first-ts (get-in @state/history [0 :ts])
          last-ts (get-in @state/history [(dec (count @state/history)) :ts])]
      {:timestamp last-ts
       :length (count (:text @state/text))
       :time (- last-ts first-ts)
       :errors (count (filter #(= (:key %) "Backspace") @state/history))
       :speed nil
       :wpm (Math/floor (wpm @state/input first-ts last-ts))
       :histogram
       []})))

(defn finished?  []
  (when (= @state/input (:text @state/text))
    (reset! state/finished true)
    (let [session (_session)]
      ;; set item history - session history map
      (ls/set-item! :sessionHistory
                    (conj (or (ls/get-item! :sessionHistory) []) session))
      [:div {:style {:margin-left "8px"}}
       "WPM: " (:wpm session)])))

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
