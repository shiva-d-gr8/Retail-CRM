package com.shivam.crm.dto;

import com.shivam.crm.model.ContactStatus;

public record StatusCount(ContactStatus status, Long count) {}