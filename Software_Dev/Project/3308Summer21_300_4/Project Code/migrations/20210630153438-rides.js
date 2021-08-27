'use strict';

module.exports = {
  up: async (queryInterface, Sequelize) => {
    /**
     * Add altering commands here.
     *
     * Example:
     * await queryInterface.createTable('users', { id: Sequelize.INTEGER });
     */
     return queryInterface.createTable('Rides', {
      id: {
        allowNull: false,
        autoIncrement: true,
        primaryKey: true,
        type: Sequelize.INTEGER
      },
      departure: {
        type: Sequelize.DATE,
        allowNull: false,
      },
      end_date: {
        type: Sequelize.DATE,
        allowNull: false,

      },
      fare_share:{
          type: Sequelize.FLOAT,
          allowNull: true,
      },
      vehicle_model: {
          type: Sequelize.STRING,
      },
      seats_available:{
          type: Sequelize.INTEGER,
          allowNull: false
      },
      driver_id: {
          type: Sequelize.INTEGER,
          references: {
              model: 'Users',
              key: 'id'
          }
      },
      dest_id: {
          type: Sequelize.INTEGER,
          references: {
              model: 'Destinations',
              key: 'id'
          }
      },
      createdAt: {
        allowNull: false,
        type: Sequelize.DATE
      },
      updatedAt: {
        allowNull: false,
        type: Sequelize.DATE
      }  
    });
  },

  down: async (queryInterface, Sequelize) => {
    /**
     * Add reverting commands here.
     *
     * Example:
     * await queryInterface.dropTable('users');
     */
     await queryInterface.dropTable('Rides');

  }
};
