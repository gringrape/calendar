// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import VModal from 'vue-js-modal'
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Popover  from 'vue-js-popover'
import VueTimepicker from 'vue2-timepicker'

Vue.use(Popover)
Vue.use(VueAxios, axios)
Vue.use(VModal, { dynamic: true, injectModalsContainer: true })
Vue.use(VueTimepicker)

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
