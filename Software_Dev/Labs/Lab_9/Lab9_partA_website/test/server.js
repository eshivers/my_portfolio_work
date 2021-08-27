// Imports the server.js file to be tested.
let server = require("../server");
//Assertion (Test Driven Development) and Should, Expect(Behaviour driven development) library
let chai = require("chai");
// Chai HTTP provides an interface for live integration testing of the API's.
let chaiHttp = require("chai-http");
chai.should();
chai.use(chaiHttp); 
const { expect } = chai;
var assert = chai.assert;




describe("Server!", () => {

    // Sample test case given to test / endpoint. 
    it("Returns the default welcome message", done => {
      chai
        .request(server)
        .get("/")
        .end((err, res) => {
          expect(res).to.have.status(200);
          expect(res.body.status).to.equals("success");
          assert.strictEqual(res.body.message, "Welcome!");
          done();
        });
    });

    // Please add your test cases here.
    it("Returns the default welcome message", done => {
      chai
        .request(server)
        .get("/operations")
        .end((err, res) => {
          expect(res).to.have.status(200);
          expect(res.body).to.be.an('array').that.is.not.empty;
          done();
        });
    });

    it("Returns the default welcome message", done => {
      const id = 1;
      chai
        .request(server)
        .get(`/operations/${id}`)
        .end((err, res) => {
          expect(res).to.have.status(200);
          expect(res.body).to.have.keys('id','name','sign')
          assert.strictEqual(res.body.id,1);
          done();
        });
    });

    it("Returns the default welcome message", done => {
      const name = "Divide";
      const sign = "/";
      chai.request(server)
        .post('/operations')
        .type('form')
        .send({
          name : name,
          sign : sign
        })
        .end((err, res) => {
          expect(res).to.have.status(201);
          assert.strictEqual(res.body.id, 4);
          assert.strictEqual(res.body.name,name);
          assert.strictEqual(res.body.sign,sign);
          done();
        });
    });
    
   

  });