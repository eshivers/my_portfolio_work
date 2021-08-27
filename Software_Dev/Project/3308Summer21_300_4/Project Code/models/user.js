'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      User.hasMany(models.Ride,{foreignKey: 'driver_id'})
      User.hasMany(models.ChatLine,{foreignKey: 'user_id'})
      User.hasMany(models.RideUser,{foreignKey: 'user_id'})
      User.hasMany(models.RideRate,{foreignKey: 'ratee_id'})
      User.hasMany(models.RideRate,{foreignKey: 'rater_id'})
      User.hasMany(models.UserFav,{foreignKey: 'user_id'})
      User.hasMany(models.Friends,{foreignKey: 'user_id'})
      User.hasMany(models.Friends,{foreignKey: 'friend_id'})
      User.hasMany(models.GroupLine,{foreignKey: 'user_id'})
      User.hasMany(models.SharedChat,{foreignKey: 'user_id'})
    }
  };
  User.init({
    name: {
      type: DataTypes.STRING,
      unique: false,
      allowNull: false
    },
    email: {
      type: DataTypes.STRING,
      unique: true,
      allowNull: false
    },
    user_rating: {
      type: DataTypes.DECIMAL,
      allowNull: false,
    },
    password: {
        type: DataTypes.STRING,
        allowNull: false
    },
    phone_no: {
        type: DataTypes.STRING(11),
        allowNull: false
    },
    dob: {
        type: DataTypes.DATEONLY,
    },
  }, {
    sequelize,
    modelName: 'User',
  });
  return User;
};
