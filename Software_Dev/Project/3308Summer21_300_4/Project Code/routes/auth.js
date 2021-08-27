// Access Control
function ensureAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
      console.log("login success");
      return next();
    } else {
      console.log("login fail");
        res.redirect('/users/login');
    }
}

module.exports = ensureAuthenticated;
