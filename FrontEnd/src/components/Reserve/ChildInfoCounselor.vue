<template>
  <div id="wrapChildContent" class="my-4">
    <div class="row col-sm-12 mt-5">
      <div class="ChildInfo col-sm-5 row">
        <div class="col-sm-4 text-muted">이름</div>
        <div class="col-sm-8 text-muted">{{ childInfo2['name'] }}</div>
      </div>
      <div class="ChildInfo col-sm-6 row">
        <div class="col-sm-4 text-muted">보호자이름</div>
        <div class="col-sm-8 text-muted">{{ childInfo2['parentName'] }}</div>
      </div>
    </div>
    <div class="row col-sm-12 mt-2 ">
      <div class="ChildInfo col-sm-5 row">
        <div class="col-sm-4 text-muted">생년월일</div>
        <div class="col-sm-8 text-muted">{{ childInfo2['birth'].slice(0, 10) }}</div>
      </div>
      <div class="ChildInfo col-sm-6 row">
        <div class="col-sm-4 text-muted">전화번호</div>
        <div class="col-sm-8 text-muted">{{ childInfo2['parentPhone'] }}</div>
      </div>
    </div>
    <div class="row col-sm-12 mt-2 ">
      <div class="ChildInfo col-sm-5 row">
        <div class="col-sm-4 text-muted">성별</div>
        <div class="col-sm-8 text-muted">{{ childInfo2['gender'] }}</div>
      </div>
    </div>
    <div class="col-sm-12 text-right col">
      <base-button type="primary" class="col-sm-2" @click="moveSurveyResult">문진표</base-button>
      <!-- <router-link to="/webCounselor" :ids="{
          child_id: childInfo2['childId'],
          parent_id: childInfo2['parentId'],
          thera_id: this.$store.state.accounts.userid
        }" class="col-sm-2">
        <base-button type="primary">상담방</base-button>
      </router-link> -->
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      childInfo2: this.$store.state.accounts.childInfo,
      theraInfo: {},
    };
  },
  methods: {
    moveSurveyResult() {
      if (this.childInfo2["surveyFlag"]) {
        this.$router.push({ name: "surveyresult", params: this.childInfo2 });
      } else {
        alert("문진표가 작성되어 있지 않습니다.")
      }
    },
  },
  created() {
    console.log("아동정보쪽")
    axios({
      url: `https://i7a606.q.ssafy.io/service-api/child/reserv-therapist/${this.$route.params.childId}`,
      method: 'get'
    })
      .then(res => {
        console.log(res.data)
        this.$store.state.accounts.childInfo = res.data
        this.$store.state.accounts.parentInfo = {
          parentId: res.data.parentId,
          parentPhone: res.data.parentPhone,
          parentName: res.data.parentName,
          profileUrl: res.data.profileUrl,
        }
      })
      .catch(err => {
        console.log(err.response)
      })
    axios({
      url: `https://i7a606.q.ssafy.io/service-api/user/${this.$store.state.accounts.userid}`,
      method: 'get',
      headers: {
        Authorization: `Bearer ${this.$store.state.accounts.accessToken}`
      }
    })
      .then(res => {
        console.log(res.data)
        this.theraInfo = res.data
      })
      .catch(err => {
        console.log(err.response)
      })
  }
};
</script>

<style></style>
