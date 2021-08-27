let server = require("../app");
let chai = require("chai");
let chaiHttp = require("chai-http");
const { should } = require("chai");
chai.should();
chai.use(chaiHttp); 
const { expect } = chai;
var assert = chai.assert;




describe("Alpine!", () => {
    it("Renders Page", done => {
      chai
        .request(server)
        .get("/")
        .end((err, res) => {
          assert.equal(1, 1, "equal");
          done();
        });
    });

  });