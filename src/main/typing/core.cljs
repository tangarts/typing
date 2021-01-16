(ns typing.core
  (:require
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [typing.components.style :as style]
   [reagent-material-ui.core.paper :refer [paper]]
   [typing.components.word :refer [word word-css]]
   [clojure.string :as str]))


; (set! *warn-on-infer* true)
; 
; (defn event-value
;   [^js/Event e]
;   (.. e -target -value))


(defn get-word-type 
  [a b]
  (cond 
    (< a b) "done"
    (= a b) "current"
    :else "awaiting"))


(defn words [expected actual]
  (let [idx (range (count expected))
        variant (for [i (range (count expected))]
                  (get-word-type i (count @actual)))] 
    (into [:div ]
          (map #(word (nth expected %1)
                      (get @actual %1) %2) idx variant))))


(def text
  (str/split
    "In today's technology landscape, the web is king. The JavaScript community is also one of the most active and prolific software development communities ever, with libraries and frameworks for any conceivable use." #"")) 



(comment 
  (for [i (range (count text))] (get-word-type i (count @input-ref)))
  (doseq
    [[i word] (map-indexed vector text)]
    [:span {:class ""
                    :style word-css} character])

  (map = (str/split @input-ref! #"") (subvec text 0 (count @input-ref!)))

  ; (fn [] (.addEventListener js/document "input" handler))
  ; (def did-mount {:component-did-mount #(.focus (rdom/dom-node %)))


)

(defn text-area [input-ref!]
  (fn []
    [:input#input {
           ; :style {:position "fixed" :left "-100%" :top "50%"} ; hidden
           :type "text"
           :auto-focus "true"
           :default-value @input-ref!
           :on-change #(reset! input-ref! (-> % .-target .-value))}]))

(defn focus []
  (-> js/document (.getElementById "input") (.focus)))

(defn app [] 
  (let [input-ref! (r/atom nil)]
  
    (fn []
      [:div {:style style/root :on-click #(focus)}
       [:div {:style style/container} 
        [paper {:elevation "4" :style style/board}
         [paper {:elevation "2"}
          [:div {:style style/statistics}
           [:div "timer: " ; FIX 
            
             ]
           [:div {:style {:margin-left "20"}}
            "CPM: " ; Fix
            ]
           [:div {:style
                  {:flex-grow "1"}}]
           [:a {:color "inherit"
                :onclick " reset fn"}
            "replay"]]]
         [:div {:style {:padding "35px 15px"}}

          [:div {:style style/inputs}
           [text-area input-ref!] ; text-area
           [words text (r/atom (str/split @input-ref! #""))]

           ]]]]])))


(defn results-dialog []
  [:results-dialog
   {:open "typing-state is finished?"
    :expected "words todo"
    :actual "input-words todo"
    :close-dialog "close-dialog"}])


(defn start []
  (rdom/render
    [app]
    (. js/document (getElementById "app"))))

(defn ^:export init []
  ;; init is called ONCE when the page loads
  ;; this is called in the index.html and must be exported
  ;; so it is available even in :advanced release builds
  (start))

(defn stop []
  ;; stop is called before any code is reloaded
  ;; this is controlled by :before-load in the config
  (js/console.log "stop"))
