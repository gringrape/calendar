<template>
  <div class="tab-pane active show" id="month" role="tabpanel" aria-labelledby="tab-month">
    <div class="daily-calendar">
      <!-- title -->
      <span class="day-name">{{ dayCal }}일 {{ weekdayCal }}</span>
      <div v-for="itinerary in itineraries">
        <div v-if="itinerary['sort'] == 2" 
             class="event-consecutive event-start event-end"
             v-popover:pop
             @click="send(itinerary['id'], 'event-consecutive-marking')">
          {{ itinerary['title'] }}
        </div>
        <div v-if="itinerary['sort'] == 1" 
             class="event event-start event-end"
             v-popover:pop
             @click="send(itinerary['id'], 'event-marking')">
          {{ itinerary['title'] }}
        </div>
        <div v-if="itinerary['sort'] == 3" 
             class="event-repeated event-start event-end"
             v-popover:pop
             @click="send(itinerary['id'], 'event-repeated-marking')">
          {{ itinerary['title'] }}
        </div>
      </div>
    </div>
    <popover name="pop">
        <div class="content-line">
          <div v-bind:class="markingClassName">
          </div>
          <div class="title">
            <h5 id="title">{{ itinerary["title"] }}</h5>
            <h5 class="reservation">
              {{ formatter(itinerary["startDate"]) }} ~ <br>
              {{ formatter(itinerary["endDate"]) }}
            </h5>
          </div>
        </div>
        <div class="content-line">
          <i class="material-icons">notes</i>
        </div>
        <div class="title">
          <h5 class="description" id="description">{{ itinerary["description"] }}</h5>
        </div>
    </popover>
  </div>
</template>

<script>
import axios from 'axios'
import moment from 'moment'

export default {
  name: "DailyCalendar",
  props: {
    yearCal: String,
    monthCal: String,
    dayCal: String,
    weekdayCal: String
  },
  data() {
    return {
      month: '',
      itineraries: [],
      // for popup
      itinerary: [],
      markingClassName: ''
    }
  },
  methods: {
    getItineraries: function() {
      this.month = this.monthCal

      if(this.month.length == 1) {
        this.month = '0' + this.month 
      }

      var today = moment(this.yearCal + this.month + this.dayCal, 
                          "YYYYMMD");

      var startMoment = today.startOf('day')

      var startDate = startMoment.format('YYYY') +
                      startMoment.format('MM') +
                      startMoment.format('DD') +
                      startMoment.format('HH') +
                      startMoment.format('mm')

      var endMoment = today.endOf('day')

      var endDate = endMoment.format('YYYY') +
                      endMoment.format('MM') +
                      endMoment.format('DD') +
                      endMoment.format('HH') +
                      endMoment.format('mm')

      console.log("시작시간" + startDate)
      console.log("끝시간" + endDate)

      axios.get('/itineraries?startDate=' + startDate + '&endDate=' + endDate)
      .then(response => this.itineraries = response.data)
    },
    send: function(id, className) {
      this.markingClassName = className;

      axios.get('/itineraries/' + id)
      .then(response => this.itinerary = response.data)
    },
    formatter: function(date) {
      return moment(date).format('YYYY') + " 년 " +
            moment(date).format("MM") + " 월 " +
            moment(date).format("DD") + " 일 " +
            moment(date).format('LT')
    }
  },
  mounted() {
    this.getItineraries();
  }
}
</script>