<template>
<div v-if="show">
  <transition name="modal">
  <div class="modal-mask">
    <div class="modal-wrapper">
  <div id="registerSchedule" tabindex="-1" role="dialog" aria-labelledby="registerScheduleLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerScheduleLabel">일정 만들기</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" @click="show = false">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label class="col-form-label">일정 제목</label>
                        <input type="text" class="form-control" id="recipient-name" v-model="title">
                    </div>
                    <div class="form-group">
                        <label for="message-text" class="col-form-label">일정 설명</label>
                        <textarea class="form-control" id="message-text" v-model="description"></textarea>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 시작 날짜</label>
                                <div class="input-group date" id="datetimepicker1" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker1" v-model="startDay"/>
                                    <div class="input-group-append" data-target="#datetimepicker1"
                                         data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="material-icons">
                                            calendar_today
                                        </i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 시작 시간</label>
                                <div class="input-group date" id="datetimepicker2" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker2" v-model="startTime"/>
                                    <div class="input-group-append" data-target="#datetimepicker2"
                                         data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="material-icons">
                                            access_time
                                        </i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 종료 날짜</label>
                                <div class="input-group date" id="datetimepicker3" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker3" v-model="endDay"/>
                                    <div class="input-group-append" data-target="#datetimepicker3"
                                         data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="material-icons">
                                            calendar_today
                                        </i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                                <label class="col-form-label">일정 종료 시간</label>
                                <div class="input-group date" id="datetimepicker4" data-target-input="nearest">
                                    <input type="text" class="form-control datetimepicker-input"
                                           data-target="#datetimepicker4" v-model="endTime"/>
                                    <div class="input-group-append" data-target="# datetimepicker4"
                                         data-toggle="datetimepicker">
                                        <div class="input-group-text"><i class="material-icons">
                                            access_time
                                        </i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1">
                            <label class="form-check-label" for="inlineCheckbox1">매월 반복</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="option2">
                            <label class="form-check-label" for="inlineCheckbox2">하루 종일</label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button @click="send()">일정 만들기</button>
                <button @click="cancel()">취소</button>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</transition>
</div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'RegisterForm',
  props: {
    checker: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      show: false,
      title: '',
      description: '',
      startDay: '',
      endDay: '',
      startTime: '',
      endTime: '',
      repetitionPeriod: 0
    }
  },
  methods: {
    send: function() {
      axios.post('/itineraries', {
        title: this.title,
        description: this.description,
        startDate: this.startDay + this.startTime,
        endDate: this.endDay + this.endTime,
        repetitionPeriod: this.repetitionPeriod
      })
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
      this.cancel();
      this.show = false;
    },
    cancel: function() {
      this.title =''
      this.description =''
      this.startDay =''
      this.endDay =''
      this.startTime =''
      this.endTime =''
    },
    hide: function() {
      this.show = false
    }
  },
  watch: {
    checker: function() {
      this.show = true
    }
  }
}
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, .5);
  display: table;
  transition: opacity .3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}
</style>