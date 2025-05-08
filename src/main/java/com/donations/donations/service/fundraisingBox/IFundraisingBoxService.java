package com.donations.donations.service.fundraisingBox;

import com.donations.donations.model.FundraisingBox;

import java.util.List;

public interface IFundraisingBoxService {

    public FundraisingBox createFundraisingBox(String currency);
    public List<FundraisingBox> getAllFundraisingBoxes();
    public void unregisterFundraisingBox(Long id);
    public void assignFundraisingBoxToEvent(Long fundraisingBoxId, Long eventId);
}
