//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.06.17 at 09:53:25 AM EDT 
//


package org.ctdbase.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 *                 A type of physical, regulatory or biochemical action in an interaction.
 *             
 * 
 * <p>Java class for axnType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="axnType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="degreecode" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="parentid" use="required" type="{http://www.w3.org/2001/XMLSchema}long" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "axnType", propOrder = {
    "value"
})
public class AxnType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "code", required = true)
    protected String code;
    @XmlAttribute(name = "degreecode", required = true)
    protected int degreecode;
    @XmlAttribute(name = "position", required = true)
    protected String position;
    @XmlAttribute(name = "parentid", required = true)
    protected long parentid;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the degreecode property.
     * 
     */
    public int getDegreecode() {
        return degreecode;
    }

    /**
     * Sets the value of the degreecode property.
     * 
     */
    public void setDegreecode(int value) {
        this.degreecode = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosition(String value) {
        this.position = value;
    }

    /**
     * Gets the value of the parentid property.
     * 
     */
    public long getParentid() {
        return parentid;
    }

    /**
     * Sets the value of the parentid property.
     * 
     */
    public void setParentid(long value) {
        this.parentid = value;
    }

}
