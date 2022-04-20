<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:template match="/students">
<html>
    <body>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Date of Birth</th>
                    <th>Sex</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Major</th>
                </tr>
            </thead>
            <tbody>
                <xsl:for-each select="student"/>
                    <tr>
                        <xsl:value-of select="@ID"/>
                        <td><xsl:value-of select="/firstName"/></td>
                        <td><xsl:value-of select="/lastNamw"/></td>
                        <td><xsl:value-of select="/email"/></td>
                        <td><xsl:value-of select="/dob"/></td>
                        <td><xsl:value-of select="sex"/></td>
                        <td><xsl:value-of select="phone"/></td>
                        <td><xsl:value-of select="status"/></td>
                        <td><xsl:value-of select="@majorId"/></td>
                    </tr>
            </tbody>
        </table>
    </body>
</html>
    </xsl:template>
</xsl:stylesheet>