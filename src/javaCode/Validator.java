package javaCode;

import javaCode.Exception.UserAlreadyExistException;
import javaCode.InLog.ReadUsers;
import javaCode.objects.*;
import javaCode.superUser.AddElements;

import java.io.FileNotFoundException;

//Class for validating all inputs
public class Validator {
    AddElements addelements= new AddElements();

    public static boolean name(String name){
        return name.matches("([a-zA-ZæøåÆØÅ]+['\\-,. ]?)+");
    }

    public static boolean phone(String phone){
        return phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*");
    }

    public static boolean email(String email){
        return email.matches(".[\\S]+@.[\\S]+[.].[\\S]+");
    }


    //Input validation for car objects
    public static boolean carId(String id){
        int idCounter = 0;
        if(id.matches("[0-9]+")){
            for(int i = 0; i < Lists.getCars().size(); i++){
                if(idCounter<=Integer.parseInt(Lists.getCars().get(i).getCarID())){
                    idCounter=Integer.parseInt(Lists.getCars().get(i).getCarID());
                }
            }
            if(idCounter >= Integer.parseInt(id)){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    public static boolean carType(String type){
        return type.matches(".{1,10}");
    }

    public static boolean carDescription(String description){
        return description.matches(".{1,50}");
    }

    public static boolean carPrice(int price){
        return price>-1 && price<1_000_000;
    }


    //Components
    //If car id matches the input id it returns true
    public static boolean carIdComponents(String ID){
        for(Car i: Lists.getCars()){
            if(i.getCarID().equals(ID)){
                return true;
            }
        }
        return false;
    }

    //If the component id matches already existing components id it returns false. Split input string with "-" to match both numbers
    public static boolean componentId(String ID){
        try{
            String[] splitInnId = ID.split("-");
            int highestcomponentTypeId = 0;

            for(Component c : Lists.getComponents()){
                String [] splitExistingId = c.getComponentID().split("-");
                String componentId = c.getComponentID();
                if(componentId.equals(ID)){
                    return false;
                }
                else if(Integer.parseInt(splitInnId[0])<0){
                    return false;
                }
                else if(splitInnId[0].equals(splitExistingId[0])){
                    //Finds highest componentId after "-" in component id-number
                    for(Component i : Lists.getComponents()){
                        if(splitInnId[0].equals(Character.toString(i.getComponentID().charAt(0)))){
                            if(highestcomponentTypeId <= Integer.parseInt(Character.toString(i.getComponentID().charAt(2)))){
                                highestcomponentTypeId = Integer.parseInt(Character.toString(i.getComponentID().charAt(2)));
                            }
                        }
                    }
                    if(Integer.parseInt(splitInnId[1]) > highestcomponentTypeId){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }

            return false;
        }catch (Exception e){
            return false;
        }

    }
    //If input type equals existing component types return true. Else choose to add new type
    public static boolean componentType(String type){
        if(type.length()==0){
            throw new IllegalArgumentException("The components type is not defined");
        }
        else{
            for(Component i : Lists.getComponents()){
                if(i.getComponentType().equals(type)){
                    return true;
                }
            }
            /*if(Dialogs.showChooseDialog("This component type is not defined. Do you want to add a new component?")){
                boolean newComponent = AddElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents(),"", "", "", 0);
                if(newComponent){
                    Dialogs.showSuccessDialog("A new component has been added");
                }
            }

             */
        }
        return false;
    }

    public static boolean componentPrice(int price){
        return price>=0;
    }


    //Adjustments
    public static boolean adjustmentID(String id){
        if(Integer.parseInt(id) < 0){
            return false;
        }
        for(Adjustment i : Lists.getAdjustment()){
            if(i.getAdjustmentID().equals(id)){
                return false;
            }
        }
        return true;
    }
    public static boolean adjustmentType(String type){
        for(Adjustment a : Lists.getAdjustment()){
            if(a.getAdjustmentType().equals(type)){
                return true;
            }
        }
        return false;
    }
    public static boolean adjustmentPrice(int price){
        return price>=0;
    }


    //Orders
    //If order number is under 1 or equals a existing number, the method returns false
    public static boolean orderNr(String nr){
        if(Integer.parseInt(nr) <= 0){
            return false;
        }
        for (Order i : Lists.getOrders()){
            if(i.getOrderNr().equals(nr)){
                return false;
            }
        }
        return true;
    }

    //Validates car id in orders
    public static boolean orderCarID(String carId){
        for(int i = 0; i < Lists.getCars().size(); i++){
            if(Lists.getCars().get(i).getCarID().equals(carId)){
                return true;
            }
        }
        return false;
    }

    public static boolean orderTotalPrice(int price){
        return price>0;
    }

    public static boolean orderCarColor(String color){
        return true;
    }
}
