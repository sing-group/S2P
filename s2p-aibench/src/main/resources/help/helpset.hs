<?xml version='1.0' encoding='ISO-8859-1'?>
<!DOCTYPE helpset PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 2.0//EN"
                         "http://java.sun.com/products/javahelp/helpset_2_0.dtd">

<helpset version="2.0">
	<title>S2P Help</title>
	<maps>
		<homeID>top</homeID>
		<mapref location="map.xml"/>
	</maps>
	
	<view mergetype="javax.help.AppendMerge">
		<name>TOC</name>
		<label>Table of Contents</label>
		<type>javax.help.TOCView</type>
		<data>toc.xml</data>
	</view>

	<view xml:lang="en">
	   <name>Search</name>
	   <label>Search</label>
	   <type>javax.help.SearchView</type>
	   <data engine="com.sun.java.help.search.DefaultSearchEngine">
    	  JavaHelpSearch
      </data>
	</view>

	<presentation default="true">
	    <name>main window</name>
	    <size width="1200" height="800" />
	    <location x="100" y="100"/>
	    <title>S2P Help</title>
	    <toolbar>
	        <helpaction>javax.help.BackAction</helpaction>
	        <helpaction>javax.help.ForwardAction</helpaction>
	        <helpaction image="homeicon">javax.help.HomeAction</helpaction>
	    </toolbar>
	</presentation>
</helpset>