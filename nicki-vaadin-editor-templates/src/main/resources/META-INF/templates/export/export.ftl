<?xml version="1.0" encoding="iso-8859-1" standalone="yes"?>
<simpleTemplate name="document">
	<data><![CDATA[
<document>
    <styles>
            <style name="right" align="RIGHT"/>
            <style name="left" align="LEFT"/>
            <style name="center" align="CENTER" vertical-align="CENTER"/>
            <style name="bottom" vertical-align="BOTTOM"/>
            <style name="top" vertical-align="TOP"/>
    </styles>
	<pages>
		<page name="Export">
			<table x="1" y="1">
				<row>
					<#list columns as column>
						<column><#if column.header??><text>${"<!["}${"CDATA["}${column.header}${"]"}${"]>"}</text></#if></column>
					</#list>
				</row>
				<#list items as item>
					<row>
						<#list columns as column>
							<column><#if column.get(item)??><text>${"<!["}${"CDATA["}${column.get(item)}${"]"}${"]>"}</text></#if></column>
						</#list>
					</row>
				</#list>
			</table>
		</page>
	</pages>
</document>
	]]></data>
</simpleTemplate>