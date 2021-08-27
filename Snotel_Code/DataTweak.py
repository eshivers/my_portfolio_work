#from matplotlib.colors import same_color
import numpy as np
#from numpy.core.defchararray import index
#from numpy.core.numeric import full
#from numpy.lib.function_base import diff
import statsmodels.api as sm
import pandas
import matplotlib.pyplot as plt
from statsmodels.stats.weightstats import ttest_ind
# import geopandas
import os
import pymannkendall as mk 
import math
import pandas
from dateutil import parser
import datetime
from statistics import mean,stdev
from functools import reduce
#from sklearn.metrics import r2_score

path = "Datasets"
start_dir = os.path.join(os.getcwd(),path)

snotel_sites = os.listdir(start_dir)
snotel_sites_withYampa = snotel_sites + ["Yampa River Basin.csv"]
full_data_sites = []

max_accumulated_swe = {}
max_ewe_swe = {}

snotel_raw_data = {}                #This is the accumulated swe of snotel sites
snotel_daily_swe = {}               #This is the daily positive swe of snotel sites
snotel_normal_swe = {}              #This is the daily non-extreme positive swe of snotel sites
snotel_ewe_swe = {}                 #This is the daily extreme swe of snotel sites
snotel_daily_swe_complete = {}      #For sites that have the complete data for 31 years
snotel_daily_ewe_swe_complete = {}  #For sites that have the complete data for 31 years
max_annual_swe = {}                 #This contains the maximum swe of each year for each snotel site
r_squared = {}

start_date = {}                     #This stores the start date of all of the datasets

total_swe = {}                                          #This is the sum of daily swe of all of the years for each snotel site
total_ewe_swe = {}                                      #This is the accumulated extreme weather swe for all the years combined

ewe_percentage_contribution = {}                        #Stores the ewe contribution percentage for each snotel site

average_annual_swe = {}                                 #This is the average annual swe for each snotel site
average_annual_ewe_swe = {}                             #This is the average annual swe contributed by ewe's for each snotel site


#Reading DATA from Snotels
for site in snotel_sites:
    file_path = os.path.join(start_dir, site)
    file_dataframe = pandas.read_csv(file_path, parse_dates=["Date"], usecols=["Date", "Snow Water Equivalent (in) Start of Day Values"], index_col=["Date"])
    file_dataframe = file_dataframe['10/1/1989' : '09/30/2020']
    snotel_raw_data[site] = file_dataframe #Adding data to coin_datas
    
    #Calculating Daily SWE from ACC SWE
    diff_dataframe = file_dataframe.diff()
    diff_dataframe = diff_dataframe.clip(lower=0)
    diff_dataframe = diff_dataframe[diff_dataframe["Snow Water Equivalent (in) Start of Day Values"] > 0]
    #print(diff_dataframe)

    snotel_daily_swe[site] = diff_dataframe

#Finds missing datasets and also updates full_data_sites
def find_missing_datasets():
    global start_date
    global full_data_sites
    startDate = {"Snotel Sites":[], "Start Year":[]}

    for site in snotel_sites:
        start_date[site] = snotel_raw_data[site].index.year[0]
        date = "10/1/" + str(start_date[site])
        startDate["Snotel Sites"].append(site[:-4])
        startDate["Start Year"].append(date)
        if start_date[site] == 1989:
            full_data_sites.append(site)

    #Adding the start date for Yampa River basin
    start_date["Yampa River Basin.csv"] = 1989
    #frame = pandas.DataFrame(startDate)
    #frame = frame.set_index('Snotel Sites')
    #frame.to_csv('start date.csv')

#Adds Yampa Data to snotel daily
def addYampa():
    global snotel_daily_swe

    #####Finding Yampa River Basin Mean EWE
    yampa_river_basin = reduce(lambda x, y: x.add(y, fill_value=0), [snotel_daily_swe[site] for site in full_data_sites])
    yampa_river_basin = yampa_river_basin/len(full_data_sites)
    
    snotel_daily_swe["Yampa River Basin.csv"] = yampa_river_basin

def preprocessing():
    global snotel_ewe_swe
    global ewe_percentage_contribution
    global average_annual_ewe_swe
    global total_ewe_swe
    global total_swe
    global max_annual_swe

    ###Adding the average yampa river basin

    for site in snotel_sites_withYampa:

        ########### Getting the snotel_positive values
        curr_snotel = snotel_daily_swe[site]

        ######CALCULATING AND STORING ONLY THE 99th Percentile
        percentile = curr_snotel.quantile(q = .99, axis = 0, numeric_only = True)
        percentile_cutoff = percentile[0]        #This is the value of our interest as all values >= are in the 99th percentile
        curr_snotel_99percentile = curr_snotel[curr_snotel['Snow Water Equivalent (in) Start of Day Values'] >= percentile_cutoff]
        
        snotel_ewe_swe[site] = curr_snotel_99percentile

        ### Saving to CSV ###
        #curr_snotel_99percentile.to_csv("Dataset/EWE SWE/99 Percentile/"+ site[:-4] + ' 99th percentile.csv')

        ##### Calculating accumulated SWE for all 31 years
        swe_total = curr_snotel.sum()[0]
        total_swe[site] = swe_total

        #######Calculating accumulated SWE for all 31 years contributed by EWE's
        swe_99percentile_total = curr_snotel_99percentile.sum()[0]
        total_ewe_swe[site] = swe_99percentile_total

        ####### Calculating percentage of accumulated SWE contributed by EWE's
        swe_99percentile_contribution_percentage = swe_99percentile_total/swe_total*100
        ewe_percentage_contribution[site] = swe_99percentile_contribution_percentage

        ############AVERAGES OF SWE's of previous step
        ### Calculating average accumulated SWE for a water year
        average_annual_swe[site] =  curr_snotel.sum()[0]/(2020-start_date[site])

        ### Calculating average accumulated SWE contributed by EWE's for a water year
        average_annual_ewe_swe[site] =  curr_snotel_99percentile.sum()[0]/(2020-start_date[site])

        # Calculating maximum daily SWE recorded for each water year
        maxByYear = {}
        for year in range (1989, 2019):
            max_val = curr_snotel['10/1/' + str(year): '12/31/' + str(year+1)].max()[0]
            maxByYear[year] = max_val
        max_annual_swe[site] = maxByYear

#Graphs Daily SWE of each snotel site
def graph_daily_swe():

    for site in snotel_sites:

        curr_snotel = snotel_daily_swe[site]

        #GRAPHING
        plt.title(site[:-4])
        plt.xlabel('Date')
        plt.ylabel('SWE (inches)')
        plt.plot(curr_snotel,label= site[:-4])

        axes = plt.gca()
        axes.set_ylim([0.25,3.5])

        plt.legend()
        figure = plt.gcf()
        figure.set_size_inches(19, 10)

        ### IF you want to save the graph uncomment this line
        plt.savefig("Graphs/Daily SWE/" + site[:-4] + "SWE .png", bbox_inches='tight',dpi=120*2)
        plt.show()

        ### Saving to CSV ###
        curr_snotel.to_csv("CSVs for data/Daily SWE/" + site[:-4] + ' positive SWE.csv')
        
#Scatters Days with extreme SWE of each snotel site
def graph_daily_ewe_swe():

    for site in snotel_sites_withYampa:
        curr_snotel = snotel_ewe_swe[site]

        #GRAPHING
        plt.title(site[:-4])
        plt.xlabel('Date')
        plt.ylabel('SWE (inches)')
        
        curr_snotel.to_csv("CSVs for data/EWE SWE/"+ site[:-4] + '.csv')

        plt.scatter(x=snotel_ewe_swe[site].index.values, y = curr_snotel,label= site[:-4])

        axes = plt.gca()
        axes.set_ylim([0.25,3.5])

        #Fitting a line
        x = np.arange(snotel_ewe_swe[site].index.values.size)
        fit = np.polyfit(x, snotel_ewe_swe[site]["Snow Water Equivalent (in) Start of Day Values"], 1)
        fit_fn = np.poly1d(fit)
        plt.plot(snotel_ewe_swe[site].index.values, fit_fn(x), color = 'C3')

        plt.legend()
        figure = plt.gcf()
        figure.set_size_inches(19, 10)

        ### IF you want to save the graph uncomment this line
        plt.savefig("Graphs/Daily EWE SWE/" + site[:-4] + "99 Percentile EWE SWE.png", bbox_inches='tight',dpi=120*2)
        plt.show()

        ### Saving to CSV ###
        curr_snotel.to_csv("CSVs for data/Daily SWE/" + site[:-4] + ' positive SWE.csv')

#Graphs the SWE Breakdown of the snotel sites + YAMPA river basin
def graph_swe_breakdown_percentage():
    totalSWE = [None] * len(snotel_sites)
    normalSWE = [None] * len(snotel_sites)

    basin_total = []
    basin_normal = []

    for i in range(len(snotel_sites)):
        site = snotel_sites_withYampa[i]
        totalSWE[i] = average_annual_swe[site]
        normalSWE[i] = average_annual_swe[site] - average_annual_ewe_swe[site]
        if site in full_data_sites:         #Appending to basin lists the sites that have full data
            basin_total.append(totalSWE[i])
            basin_normal.append(normalSWE[i])

    #Taking mean of the lists to find the average SWE
    totalSWE.append(mean(basin_total))
    normalSWE.append(mean(basin_normal))

    #GRAPHING
    fig, ax = plt.subplots() 
    # creating the bar plot
    first_bars = ax.barh([site[:-4] for site in snotel_sites_withYampa], totalSWE, label = "Extreme Weather SWE per Water Year", color = 'C1')

    second_bars = ax.barh([site[:-4] for site in snotel_sites_withYampa], normalSWE, label = "Non-extreme SWE per Water Year", color = 'C0')
    
    ax.set_xlabel("Total SWE (inches)")
    ax.set_ylabel("Snotel Sites")
    plt.title("SWE Breakdown of Yampa River Basin")
    ax.legend()


    #bars = plt.barh(x, height=y, width=.4)
    for index in range (len(snotel_sites_withYampa)):
        bar = first_bars[index]

        w,h = bar.get_width(), bar.get_height()
        x0, y0 = bar.xy
        x3, y3 = x0+w, y0+h
        plt.text(x3+.25,y3-.35, "{:.2f}".format((totalSWE[index]-normalSWE[index])*100/totalSWE[index]), color = "C0", va = 'center')
    #    yval = bar.get_height()
    #    plt.text(bar.get_x(), yval + .005, yval)

    for index in range (len(snotel_sites_withYampa)):
        bar = second_bars[index]
        w,h = bar.get_width(), bar.get_height()
        x0, y0 = bar.xy
        plt.text(x0,y0+.33, "{:.2f}".format(normalSWE[index]*100/totalSWE[index]), color = "C1", va = 'center')
    #    yval = bar.get_height()
    #    plt.text(bar.get_x(), yval + .005, yval)

    plt.legend(bbox_to_anchor=(1.05, 1))
    figure = plt.gcf()
    figure.set_size_inches(19, 10)

    plt.savefig("Graphs/SWE Breakdown/" + "99 Percentile SWE Breakdown.png", bbox_inches='tight',dpi=120*2)
    plt.show()

#Graphs the SWE Breakdown of the snotel sites + YAMPA river basin
def graph_breakdown_percentage():
    totalSWE = [None] * len(snotel_sites)
    normalSWE = [None] * len(snotel_sites)

    basin_total = []
    basin_normal = []

    percentage_normalSWE = [None] * len(snotel_sites_withYampa)
    percentage_totalSWE = [None] * len(snotel_sites_withYampa)

    for i in range(len(snotel_sites)):
        site = snotel_sites_withYampa[i]
        totalSWE[i] = average_annual_swe[site]
        normalSWE[i] = average_annual_swe[site] - average_annual_ewe_swe[site]

        if site in full_data_sites:         #Appending to basin lists the sites that have full data
            basin_total.append(totalSWE[i])
            basin_normal.append(normalSWE[i])

    #Taking mean of the lists to find the average SWE
    totalSWE.append(mean(basin_total))
    normalSWE.append(mean(basin_normal))

    #Calculating the percentages
    for i in range(len(snotel_sites_withYampa)):
        percentage_normalSWE[i] = normalSWE[i]*100/totalSWE[i]
        percentage_totalSWE[i] = 100


    #GRAPHING
    fig, ax = plt.subplots() 
    # creating the bar plot
    first_bars = ax.barh([site[:-4] for site in snotel_sites_withYampa], percentage_totalSWE, label = "Contribution of Extreme Weather", color = 'C1')

    second_bars = ax.barh([site[:-4] for site in snotel_sites_withYampa], percentage_normalSWE, label = "Contribution of Normal Weather", color = 'C0')
    
    ax.set_xlabel("Percentage of total SWE")
    ax.set_ylabel("Snotel Sites")
    plt.title("SWE Breakdown of Yampa River Basin")
    ax.legend()


    #bars = plt.barh(x, height=y, width=.4)
    for index in range (len(snotel_sites_withYampa)):
        bar = first_bars[index]

        w,h = bar.get_width(), bar.get_height()
        x0, y0 = bar.xy
        x3, y3 = x0+w, y0+h
        plt.text(x3+.25,y3-.35, "{:.2f}".format((totalSWE[index]-normalSWE[index])*100/totalSWE[index]), color = "C0", va = 'center')
    #    yval = bar.get_height()
    #    plt.text(bar.get_x(), yval + .005, yval)

    for index in range (len(snotel_sites_withYampa)):
        bar = second_bars[index]
        w,h = bar.get_width(), bar.get_height()
        x0, y0 = bar.xy
        plt.text(x0,y0+.33, "{:.2f}".format(normalSWE[index]*100/totalSWE[index]), color = "C1", va = 'center')
    #    yval = bar.get_height()
    #    plt.text(bar.get_x(), yval + .005, yval)

    plt.legend(bbox_to_anchor=(1.05, 1))
    figure = plt.gcf()
    figure.set_size_inches(19, 10)

    plt.savefig("Graphs/SWE Breakdown/" + "99 Percentile SWE Percentage Breakdown.png", bbox_inches='tight',dpi=120*2)
    plt.show()

#Graphs the maximum SWE recorded for each water Year for each snotel site
def graph_max_annual_swe():


    #First 9 sites
    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[:9]:
        plt.subplot(3,3,i)
        plt.scatter(max_annual_swe[site].keys(), max_annual_swe[site].values(), label= site[:-4])
        
        axes = plt.gca()
        axes.set_ylim([.5,3.5])

        plt.title(site[:-4])
        i+=1

    plt.xlabel("Water Years")
    plt.ylabel("SWE(inches)")
    plt.suptitle("Annual Maximum SWE")
    figure = plt.gcf()
    figure.set_size_inches(20, 11)
    plt.savefig("Graphs/Annual Maximum SWE/" + "Annual Maximum SWE(1).png", bbox_inches='tight', dpi=120*2)
    plt.show()

    #9-18 Sites
    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[9:18]:
        plt.subplot(3,3,i)
        plt.scatter(max_annual_swe[site].keys(), max_annual_swe[site].values(), label= site[:-4])
        
        axes = plt.gca()
        axes.set_ylim([.5,3.5])

        plt.title(site[:-4])
        i+=1

    plt.xlabel("Water Years")
    plt.ylabel("SWE(inches)")
    plt.suptitle("Annual Maximum SWE")
    figure = plt.gcf()
    figure.set_size_inches(20, 11)
    plt.savefig("Graphs/Annual Maximum SWE/" + "Annual Maximum SWE(2).png", bbox_inches='tight', dpi=120*2)
    plt.show()

    #18 onward Sites
    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[18:]:
        plt.subplot(2,2,i)
        plt.scatter(max_annual_swe[site].keys(), max_annual_swe[site].values(), label= site[:-4])
        
        axes = plt.gca()
        axes.set_ylim([.5,3.5])

        plt.title(site[:-4])
        i+=1

    plt.xlabel("Water Years")
    plt.ylabel("SWE(inches)")
    plt.suptitle("Annual Maximum SWE")
    figure = plt.gcf()
    figure.set_size_inches(20, 11)
    plt.savefig("Graphs/Annual Maximum SWE/" + "Annual Maximum SWE(3).png", bbox_inches='tight', dpi=120*2)
    plt.show()

# Graphs the EWE events of each snotel site
def graph_ewe_days_all():

    #GRAPHING PART 1
    fig = plt.figure()
    
    for site in snotel_sites_withYampa:
        df = snotel_ewe_swe[site].astype(str)
        df['Snow Water Equivalent (in) Start of Day Values'] = df['Snow Water Equivalent (in) Start of Day Values'].replace([row[0] for _,row in df.iterrows()],site[:-4])
        df = df['10/1/1989' : '09/30/2000']
        plt.scatter(df.index.values, df["Snow Water Equivalent (in) Start of Day Values"], label= site[:-4], marker='x')

    plt.xlabel("Water Years")
    plt.ylabel("Snotel Sites")
    plt.legend(bbox_to_anchor=(1.05, 1))
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    #Uncomment to save plot
    plt.savefig("Graphs/EWE Days All/" + "99 Percentile EWE Days(1).png", bbox_inches='tight', dpi=120*2)
    plt.show()


    #GRAPHING PART 2
    fig = plt.figure()
    
    for site in snotel_sites_withYampa:
        df = snotel_ewe_swe[site].astype(str)
        df['Snow Water Equivalent (in) Start of Day Values'] = df['Snow Water Equivalent (in) Start of Day Values'].replace([row[0] for _,row in df.iterrows()],site[:-4])
        df = df['10/1/1999' : '09/30/2010']
        plt.scatter(df.index.values, df["Snow Water Equivalent (in) Start of Day Values"], label= site[:-4], marker='x')

    plt.xlabel("Water Years")
    plt.ylabel("Snotel Sites")
    plt.legend(bbox_to_anchor=(1.05, 1))
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    #Uncomment to save plot
    plt.savefig("Graphs/EWE Days All/" + "99 Percentile EWE Days(2).png", bbox_inches='tight', dpi=120*2)
    plt.show()

    #GRAPHING PART 3
    fig = plt.figure()
    
    for site in snotel_sites_withYampa:
        df = snotel_ewe_swe[site].astype(str)
        df['Snow Water Equivalent (in) Start of Day Values'] = df['Snow Water Equivalent (in) Start of Day Values'].replace([row[0] for _,row in df.iterrows()],site[:-4])
        df = df['10/1/2009' : '09/30/2020']
        plt.scatter(df.index.values, df["Snow Water Equivalent (in) Start of Day Values"], label= site[:-4], marker='x')

    plt.xlabel("Water Years")
    plt.ylabel("Snotel Sites")
    plt.legend(bbox_to_anchor=(1.05, 1))
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    #Uncomment to save plot
    plt.savefig("Graphs/EWE Days All/" + "99 Percentile EWE Days(3).png", bbox_inches='tight', dpi=120*2)
    plt.show()    

def store_r_square():
    global r_squared

    for index in range(len(snotel_sites)):
        site = snotel_sites[index]
        x = np.arange(snotel_ewe_swe[site].index.values.size)
        fit = np.polyfit(x, snotel_ewe_swe[site]["Snow Water Equivalent (in) Start of Day Values"], 1)
        fit_fn = np.poly1d(fit)

        #line_fit[site] = fit_fn
        r_square_val = np.corrcoef(x,snotel_ewe_swe[site]["Snow Water Equivalent (in) Start of Day Values"])
        r_squared[site] = r_square_val[0]

    #line_fit_df = pandas.DataFrame(list(line_fit.items()),columns = ['Snotel Site', 'Line Fit'])
    r_squared_df = pandas.DataFrame(list(r_squared.items()),columns = ['Snotel Site', 'R Square'])

    #line_fit_df.to_csv('line fit.csv')
    r_squared_df.to_csv('r_squared.csv')
    
    #print(line_fit)

def get_max_accumulated_swe():
    global max_accumulated_swe
    for site in snotel_sites_withYampa:
        start_date = datetime.datetime(1989, 10, 1)
        end_date = datetime.datetime(1990,9,30)
        curr_site = snotel_raw_data[site]
        max_accumulated_swe[site] = {}
        while end_date.year < 2021:
            year_data = curr_site[start_date: end_date]
            max_val = curr_site[start_date: end_date]['Snow Water Equivalent (in) Start of Day Values'].max()
            if not math.isnan(max_val):
                max_accumulated_swe[site][end_date.year] = max_val
            

            start_date = datetime.datetime(end_date.year,10,1)
            end_date = datetime.datetime(end_date.year + 1,9,30)


def add_yampa_max_accumulated_swe():
    global max_accumulated_swe

    #####Finding Yampa River Basin Mean EWE
    yampa_river_basin = reduce(lambda x, y: x.add(y, fill_value=0), [snotel_raw_data[site] for site in full_data_sites])
    yampa_river_basin = yampa_river_basin/len(full_data_sites)
    
    snotel_raw_data["Yampa River Basin.csv"] = yampa_river_basin

def add_yampa_max_ewe_swe():
    global max_ewe_swe
    max_ewe_swe['Yampa River Basin.csv'] = {}
    curr_year = 1990
    file_dataframe = pandas.read_csv('Yampa River Basin_0.5.csv', parse_dates=["Date"], usecols=["Date", "Snow Water Equivalent (in) Start of Day Values"], index_col=["Date"])
    yampa_ewe = file_dataframe['10/1/1989' : '09/30/2020']
    start_date = datetime.datetime(1989, 10, 1)
    end_date = datetime.datetime(1990,9,30)
    site = 'Yampa River Basin.csv'
    while end_date.year < 2021:
        year_data = yampa_ewe[start_date: end_date]
        max_val = yampa_ewe[start_date: end_date]['Snow Water Equivalent (in) Start of Day Values'].max()
        if not math.isnan(max_val):
            max_ewe_swe[site][end_date.year] = max_val
        

        start_date = datetime.datetime(end_date.year,10,1)
        end_date = datetime.datetime(end_date.year + 1,9,30)
            
    

def graph_max_accumulated_swe():
    for site in max_accumulated_swe:
        fig, ax = plt.subplots() 
        ax.set_ylabel("Max SWE")
        plt.title("Maximum SWE Interannual Variability _ " + site[:-4])
        
        x = list(max_accumulated_swe[site].keys())
        y = list(max_accumulated_swe[site].values())
        
        ax.bar(x,y)
        plt.xlim([1989, 2021])
        plt.ylim([0, 50])
        x_samp = sm.add_constant(x) # adding a constant
        model = sm.OLS(y,x_samp).fit()
        fit = model.params.tolist()
        fit.reverse()
        fit_fn = np.poly1d(fit)
        T_test = ttest_inx_samp = sm.add_constant(x) # adding a constant
        model = sm.OLS(y,x_samp).fit()
        fit = model.params.tolist()
        fit.reverse()
        result = mk.original_test(y)
        print("Maximum SWE Interannual Variability")
        print(site)
        print("P-Value: ",result.p)
        if(result.p > .05):
            print("There is no significant trend in the data\n")
        else:
            print("There is a significant trend in the data\n")
        print("Mean: ",mean(y))
        print("Standard Deviation: ",stdev(y))
        if site != "Yampa River Basin.csv":
            plt.plot(x, fit_fn(x), color = 'C3')

        # plt.show()
        plt.savefig(os.path.join("Graphs","Accumulated_Max_SWE","Maximum SWE Interannual Variability _ " + site[:-4] + ".png"))

def graph_max_accumulated_swe_combined():
    #First 9 sites
    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[:9]:
        plt.subplot(3,3,i)
        plt.tight_layout()


        x = list(max_accumulated_swe[site].keys())
        y = list(max_accumulated_swe[site].values())
        plt.bar(x,y)

        axes = plt.gca()
        plt.title(site[:-4])
        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 50])
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')
        
        i += 1

    plt.xlabel("Water Years")
    plt.ylabel("Max EWE SWE")
    plt.suptitle("Maximum EWE SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)

    # plt.show()
    plt.savefig(os.path.join("Graphs","Accumulated_Max_SWE_Com","Maximum SWE Interannual Variability Combined_1.png"))


    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[9:18]:
        plt.subplot(3,3,i)
        plt.tight_layout()


        x = list(max_accumulated_swe[site].keys())
        y = list(max_accumulated_swe[site].values())
        plt.bar(x,y)

        axes = plt.gca()
        plt.title(site[:-4])

        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 50])
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')
        i += 1

    plt.xlabel("Water Years")
    plt.ylabel("Max EWE SWE")
    plt.suptitle("Maximum EWE SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    # plt.show()
    plt.savefig(os.path.join("Graphs","Accumulated_Max_SWE_Com","Maximum SWE Interannual Variability Combined_2.png"))


    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[18:]:
        plt.subplot(2,2,i)


        x = list(max_accumulated_swe[site].keys())
        y = list(max_accumulated_swe[site].values())
        plt.bar(x,y)

        axes = plt.gca()
        plt.title(site[:-4])
        plt.tight_layout()
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')


        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 50])
        i += 1

    plt.xlabel("Water Years")
    plt.ylabel("Max SWE")
    plt.suptitle("Maximum SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    # plt.show()
    plt.savefig(os.path.join("Graphs","Accumulated_Max_SWE_Com","Maximum SWE Interannual Variability Combined_3.png"))



def get_max_ewe_swe():
    global max_ewe_swe
    for site in snotel_sites:
        start_date = datetime.datetime(1989, 10, 1)
        end_date = datetime.datetime(1990,9,30)
        curr_site = snotel_ewe_swe[site]
        max_ewe_swe[site] = {}
        while end_date.year < 2021:
            year_data = curr_site[start_date: end_date]
            max_val = curr_site[start_date: end_date]['Snow Water Equivalent (in) Start of Day Values'].max()
            if not math.isnan(max_val):
                max_ewe_swe[site][end_date.year] = max_val
            

            start_date = datetime.datetime(end_date.year,10,1)
            end_date = datetime.datetime(end_date.year + 1,9,30)
            


def graph_max_ewe_swe_combined():
    #First 9 sites
    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[:9]:
        plt.subplot(3,3,i)
        plt.tight_layout()


        x = list(max_ewe_swe[site].keys())
        y = list(max_ewe_swe[site].values())
        plt.bar(x,y)
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        
        axes = plt.gca()
        plt.title(site[:-4])
        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 4])
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')
        i += 1
        

    plt.xlabel("Water Years")
    plt.ylabel("Max EWE SWE")
    plt.suptitle("Maximum EWE SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)

    # plt.show()
    plt.savefig(os.path.join("Graphs","Max_EWE_SWE_Com","Maximum EWE SWE Interannual Variability Combined_1.png"))

    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[9:18]:
        plt.subplot(3,3,i)
        plt.tight_layout()


        x = list(max_ewe_swe[site].keys())
        y = list(max_ewe_swe[site].values())
        plt.bar(x,y)
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        
        axes = plt.gca()
        plt.title(site[:-4])
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')
        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 4])
        
        i += 1

    plt.xlabel("Water Years")
    plt.ylabel("Max EWE SWE")
    plt.suptitle("Maximum EWE SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    # plt.show()
    plt.savefig(os.path.join("Graphs","Max_EWE_SWE_Com","Maximum EWE SWE Interannual Variability Combined_2.png"))


    fig = plt.figure()
    i=1
    for site in snotel_sites_withYampa[18:]:
        plt.subplot(2,2,i)


        x = list(max_ewe_swe[site].keys())
        y = list(max_ewe_swe[site].values())
        plt.bar(x,y)
        fit = np.polyfit(x,y,deg=1)
        fit_fn = np.poly1d(fit)
        
        
        axes = plt.gca()
        plt.title(site[:-4])
        plt.tight_layout()

        axes.set_xlim([1989, 2021])
        axes.set_ylim([0, 4])
        if site != "Yampa River Basin.csv":
            axes.plot(x, fit_fn(x), color = 'C3')
        
        i += 1

    plt.xlabel("Water Years")
    plt.ylabel("Max EWE SWE")
    plt.suptitle("Maximum EWE SWE Interannual Variability")
    figure = plt.gcf()
    figure.set_size_inches(19, 10)
    # plt.show()
    plt.savefig(os.path.join("Graphs","Max_EWE_SWE_Com","Maximum EWE SWE Interannual Variability Combined_3.png"))


def graph_max_ewe_swe():
    for site in max_ewe_swe:
        fig, ax = plt.subplots() 
        ax.set_ylabel("Max EWE SWE")
        plt.title("Maximum EWE SWE Interannual Variability _ " + site[:-4])
        x = list(max_ewe_swe[site].keys())
        y = list(max_ewe_swe[site].values())

        ax.bar(x,y)

        x_samp = sm.add_constant(x) # adding a constant
        model = sm.OLS(y,x_samp).fit()
        fit = model.params.tolist()
        fit.reverse()
        fit_fn = np.poly1d(fit)
        plt.xlim([1989, 2021])
        plt.ylim([0, 4])

        T_test = ttest_ind(x,y)
        result = mk.original_test(y)
        print("Maximum EWE SWE Interannual Variability")
        print(site)
        print("P-Value: ",result.p)
        if(result.p > .05):
            print("There is no significant trend in the data\n")
        else:
            print("There is a significant trend in the data\n")
        print("Mean: ",mean(y))
        print("Standard Deviation: ",stdev(y))
        
        if site != "Yampa River Basin.csv":
            plt.plot(x, fit_fn(x), color = 'C3')
            
        # plt.show()
        plt.savefig(os.path.join("Graphs","Max_EWE_SWE","Maximum EWE SWE Interannual Variability _ " + site[:-4] + ".png"))


def main():
    find_missing_datasets()
    addYampa()
    preprocessing()
    # graph_daily_swe()
    # graph_daily_ewe_swe()
    # graph_max_annual_swe()
    # add_yampa_max_accumulated_swe()
    # get_max_accumulated_swe()
    # graph_max_accumulated_swe()
    get_max_ewe_swe()
    add_yampa_max_ewe_swe()
    # print(max_ewe_swe['Yampa River Basin.csv'])
    graph_max_ewe_swe()
    graph_max_ewe_swe_combined()
    # graph_max_accumulated_swe_combined()
    # shpy = geopandas.read_file('./Shapefiles/b_cbrfc.dbf')
    # print(shpy.shape)
    # print(shpy.head())
    # shpy.plot()
    # plt.show()

    # graph_swe_breakdown_percentage()
    # graph_ewe_days_all()
    # graph_breakdown_percentage()

    # store_r_square()

if __name__ == "__main__":
    main()
