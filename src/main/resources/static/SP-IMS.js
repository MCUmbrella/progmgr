var v1 = new Vue({
  el: "#app1",
  methods: {
    handleClick(row) {
      console.log(row);
    },

    async getData(pageNum) {
      const { data: res } = await axios({
        method: "get",
        url: "/get/programList",
        params: {
          pageNum,
        },
        headers: {
          "content-type": "application/json",
        },
      });
      console.log(res.data.programResults)
      // res.data.forEach((item) => {
      //   item.actorList = this.changeArrToStr(item.actorList);
      // });

      this.tableData = res.data.programResults;
      console.log(this.tableData)
    },
    changeArrToStr(arr) {
      let newArr = [];
      arr.forEach((item) => newArr.push(item.name));
      return newArr.join(",");
    },
    pageChange(pageNum) {
      console.log(pageNum);
      if (pageNum <= 0) {
        return;
      } else {
        this.getData(pageNum);
      }
    },
    add() {
      this.isAdd = true
    },
    addConfirm() {
      this.isAdd = false
      this.addData(this.addForm)
    },
    async addData(addForm) {
      const { data: res } = await axios({
        method: "post",
        url: "/add/program",
        data: {
          type: addForm.category,
          name: addForm.program,
          actors: addForm.actor,
          point: addForm.point
        },
        headers: {
          "content-type": "application/json",
        },
      });
      console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '新增成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }

    },
    edit(row) {
      this.editForm.programId = row.id
      this.editForm.category = row.typeName
      this.editForm.program = row.name
      this.editForm.actor = row.actorList
      this.editForm.point = row.view
      this.isEdit = true
    },
    editConfirm() {
      this.isEdit = false,
        this.editData(this.editForm)
    },
    async editData(editForm) {
      const { data: res } = await axios({
        method: "post",
        url: "/update/program",
        data: {
          id: editForm.programId,
          type: editForm.category,
          name: editForm.program,
          actors: editForm.actor,
          point: editForm.point
        },
        headers: {
          "content-type": "application/json",
        },
      });
      console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '编辑成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }

    },
    async search(searchForm) {
      const { data: res } = await axios({
        method: "get",
        url: "/get/search",
        params: {
          type: searchForm.category,
          num: searchForm.actorNum,
          name: searchForm.program
        },
        headers: {
          "content-type": "application/json",
        },
      });
      console.log(res)
      // res.data.forEach((item) => {
      //   item.actorList = this.changeArrToStr(item.actorList);
      // });

      this.tableData = res.data.programSearchResults;
      console.log(res.data.programSearchResults)
      console.log(this.tableData)
    },
    async deleteData(index) {
      const { data: res } = await axios({
        method: "post",
        url: "/delete/program",
        data: {
          id: index
        },
        headers: {
          "content-type": "application/json",
        },
      });
      console.log(res)
      if (res.code == '0') {
        this.getData(this.pageNum);
        this.$message({
          message: '删除成功',
          type: 'success'
        });
      } else {
        this.$message.error(res.message);
      }

    },
  },
  data() {
    return {
      cateOptions: [{
        value: '歌舞',
        label: '歌舞'
      }, {
        value: '戏曲',
        label: '戏曲'
      }, {
        value: '小品',
        label: '小品'
      }, {
        value: '相声',
        label: '相声'
      }, {
        value: '武术',
        label: '武术'
      }, {
        value: '其他',
        label: '其他'
      }],

      addForm: {
        category: null,
        program: null,
        actor: null,
        point: null
      },
      searchForm: {
        category: null,
        actorNum: null,
        program: null,
      },
      editForm: {
        category: null,
        program: null,
        actor: null,
        point: null
      },
      value: '',
      isEdit: false,
      isAdd: false,
      tableData: [],
      pageNum: 1,
    };
  },
  created() {
    this.getData(this.pageNum);
  },
});
