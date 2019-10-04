<template>
  <div class="calendar-container">
      <div class="calendar-header">
          <button class="btn btn-light">Today</button>
          <div class="ico-arrow">
              <a href="#" data-toggle="tooltip" data-placement="top" title="이전">
                  <i class="material-icons" @click="getPrevMonth()">
                    arrow_left
                  </i>
              </a>
              <a href="#" data-toggle="tooltip" data-placement="top" title="다음">
                  <i class="material-icons" @click="getNextMonth()">
                    arrow_right
                  </i>
              </a>
          </div>
          <h4>{{year}}년 {{month}}월</h4>
          <ul class="nav nav-tabs view-tab" id="view" role="tablist">
              <li class="nav-item">
                  <a class="nav-link active" id="tab-month" data-toggle="tab" href="#month" role="tab"
                    aria-controls="month"
                    aria-selected="true">월</a>
              </li>
              <li class="nav-item">
                  <a class="nav-link" id="tab-day" data-toggle="tab" href="#day" role="tab" aria-controls="day"
                    aria-selected="false">일</a>
              </li>
          </ul>
      </div>
      <router-view :yearCal="year" :monthCal="month" :counter="componentKey" :key="componentKey" @registered="forceUpdateRegistered()"></router-view>
  </div>
</template>

<script>
import Vue from 'vue'
import moment from 'moment'

export default {
  name: 'App',
  data() {
    return {
      year: '',
      month: '',
      componentKey: 0
    }
  },
  methods: {
    initialize: function() {
      this.year = moment().format('YYYY')
      this.month = moment().format('M')
    },
    getPrevMonth: function() {
      var curr = moment(this.year + this.month, 'YYYYM')
      var prev = curr.subtract(1, 'months')
      this.year = prev.format('YYYY')
      this.month = prev.format('M')
      this.forceUpdate();
    },
    getNextMonth: function() {
      var curr = moment(this.year + this.month, 'YYYYM')
      var next = curr.add(1, 'months')
      this.year = next.format('YYYY')
      this.month = next.format('M')
      this.forceUpdate();
    },
    forceUpdate: function() {
      this.componentKey += 1;
      console.log("업데이트~!!!")
    }
  },
  mounted() {
    this.initialize()
  }
}
</script>

<style>
@import url("https://fonts.googleapis.com/icon?family=Material+Icons"); 
@import "assets/css/basic.css";
@import 'vue2-timepicker/dist/VueTimepicker.css'
</style>
