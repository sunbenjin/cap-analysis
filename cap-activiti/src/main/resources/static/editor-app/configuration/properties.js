/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
'use strict';

var KISBPM = KISBPM || {};
KISBPM.PROPERTY_CONFIG =
{
    "string": {
        "readModeTemplateUrl": "editor-app/configuration/properties/default-value-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/string-property-write-mode-excel_template.html"
    },
    "boolean": {
        "templateUrl": "editor-app/configuration/properties/boolean-property-excel_template.html"
    },
    "text" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/default-value-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/text-property-write-excel_template.html"
    },
    "kisbpm-multiinstance" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/default-value-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/multiinstance-property-write-excel_template.html"
    },
    "oryx-formproperties-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/form-properties-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/form-properties-write-excel_template.html"
    },
    "oryx-executionlisteners-multiplecomplex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/execution-listeners-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/execution-listeners-write-excel_template.html"
    },
    "oryx-tasklisteners-multiplecomplex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/task-listeners-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/task-listeners-write-excel_template.html"
    },
    "oryx-eventlisteners-multiplecomplex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/event-listeners-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/event-listeners-write-excel_template.html"
    },
    "oryx-usertaskassignment-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/assignment-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/assignment-write-excel_template.html"
    },
    "oryx-servicetaskfields-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/fields-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/fields-write-excel_template.html"
    },
    "oryx-callactivityinparameters-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/in-parameters-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/in-parameters-write-excel_template.html"
    },
    "oryx-callactivityoutparameters-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/out-parameters-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/out-parameters-write-excel_template.html"
    },
    "oryx-subprocessreference-complex": {
        "readModeTemplateUrl": "editor-app/configuration/properties/subprocess-reference-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/subprocess-reference-write-excel_template.html"
    },
    "oryx-sequencefloworder-complex" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/sequenceflow-order-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/sequenceflow-order-write-excel_template.html"
    },
    "oryx-conditionsequenceflow-complex" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/condition-expression-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/condition-expression-write-excel_template.html"
    },
    "oryx-signaldefinitions-multiplecomplex" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/signal-definitions-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/signal-definitions-write-excel_template.html"
    },
    "oryx-signalref-string" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/default-value-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/signal-property-write-excel_template.html"
    },
    "oryx-messagedefinitions-multiplecomplex" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/message-definitions-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/message-definitions-write-excel_template.html"
    },
    "oryx-messageref-string" : {
        "readModeTemplateUrl": "editor-app/configuration/properties/default-value-display-excel_template.html",
        "writeModeTemplateUrl": "editor-app/configuration/properties/message-property-write-excel_template.html"
    }
};
