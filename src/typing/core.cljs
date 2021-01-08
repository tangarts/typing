(ns ^:figwheel-hooks typing.core
  (:require
   [goog.dom :as gdom]
   [reagent.core :as reagent :refer [atom]]
   [reagent.dom :as rdom]))


(defonce app-state (atom {:text "Hello world!"}))

(defn get-app-element []
  (gdom/getElement "app"))

(defn typing-area []
  [:div {:id "typing-area"}
   [:div {:id "text-display"}]
   [:div {:class "bar"}
    [:input {:id "input-field"
             :type "text"
             :spellcheck "false"
             :autocomplete "off"
             :autocorrect  "off"
             :autocapitalize "off"
             :tabindex "1"}]
    [:button {:id "redo-button" :tabindex "2" :text "redo"}]]])

(defn hello-world []
  [:div
   [:h1 (:text @app-state)]
   [:h3 "Edit this in src/typing/core.cljs and watch it change!"]])

(defn mount [el]
  (rdom/render [typing-area] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^;after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
